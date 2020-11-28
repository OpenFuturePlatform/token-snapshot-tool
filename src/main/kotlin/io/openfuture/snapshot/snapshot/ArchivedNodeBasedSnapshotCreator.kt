package io.openfuture.snapshot.snapshot

import io.openfuture.snapshot.domain.WalletState
import org.web3j.abi.EventEncoder
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.exceptions.ClientConnectionException
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

class ArchivedNodeBasedSnapshotCreator(nodeAddress: String) : SnapshotCreator {

    private val web3j: Web3j = Web3j.build(HttpService(nodeAddress))

    override fun snapshot(contractAddress: String, fromBlock: Int, toBlock: Int): List<WalletState> {
        val addresses = batchFetchAddresses(contractAddress, fromBlock, toBlock)

        println("Fetched ${addresses.size} addresses")

        return addresses.map {
            println("Getting balance for $it")
            val balance = getTokenBalanceAtBlock(it, contractAddress, toBlock)

            val converted = Convert.fromWei(balance, Convert.Unit.GWEI)

            WalletState(it, converted)
        }
    }

    private fun batchFetchAddresses(contractAddress: String, fromBlock: Int, toBlock: Int): Set<String> {
        val addresses = mutableSetOf<String>()

        for (blockNumber in fromBlock..toBlock step BATCH_SIZE) {
            var nextBatch = blockNumber + BATCH_SIZE - 1
            if (nextBatch >= toBlock) nextBatch = toBlock

            println("Batch fetching addresses from $blockNumber to $nextBatch blocks")
            val logs = getTransferLogs(contractAddress, blockNumber, nextBatch)
            val fetchedAddresses = fetchAddressesFromLogs(logs)
            addresses.addAll(fetchedAddresses)
        }

        return addresses
    }

    private fun getTransferLogs(contractAddress: String, fromBlock: Int, toBlock: Int): List<EthLog.LogResult<Any>> {
        return try {
            val transferFilter = createTransferFilter(contractAddress, fromBlock, toBlock)
            web3j.ethGetLogs(transferFilter).send().logs
        } catch (ex: ClientConnectionException) {
            getTransferLogs(contractAddress, fromBlock, toBlock)
        }
    }

    private fun decodeAddress(rawData: String) =
            FunctionReturnDecoder.decodeIndexedValue(rawData, object : TypeReference<Address>() {}) as Address

    private fun createTransferFilter(contractAddress: String, fromBlock: Int, toBlock: Int): EthFilter {
        return EthFilter(
                DefaultBlockParameter.valueOf(fromBlock.toBigInteger()),
                DefaultBlockParameter.valueOf(toBlock.toBigInteger()),
                contractAddress
        )
                .addSingleTopic(EventEncoder.encode(
                        Event(
                                TRANSFER_EVENT,
                                listOf(object : TypeReference<Address>() {}, object : TypeReference<Address>() {},
                                        object : TypeReference<Uint256>() {})
                        )))
    }

    private fun fetchAddressesFromLogs(transferLogs: List<EthLog.LogResult<Any>>): Set<String> {
        return transferLogs.map {
            val topics: List<String> = (it.get() as EthLog.LogObject).topics

            decodeAddress(topics[2]).value
        }.toSet()
    }

    private fun getTokenBalanceAtBlock(address: String, tokenAddress: String, blockNumber: Int): BigDecimal {
        return try {
            val function = Function(BALANCE_METHOD, listOf(Address(address)), listOf(object : TypeReference<Uint256>() {}))
            val encodedFunction = FunctionEncoder.encode(function)
            val result: EthCall = web3j.ethCall(
                    Transaction.createFunctionCallTransaction(null, null, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, tokenAddress, encodedFunction),
                    DefaultBlockParameter.valueOf(blockNumber.toBigInteger()))
                    .send()

            (FunctionReturnDecoder.decode(result.value, function.outputParameters).first().value as BigInteger).toBigDecimal()
        } catch (ex: ClientConnectionException) {
            getTokenBalanceAtBlock(address, tokenAddress, blockNumber)
        }
    }

    companion object {
        private const val BATCH_SIZE = 20
        const val TRANSFER_EVENT = "Transfer"
        const val BALANCE_METHOD = "balanceOf"
    }

}
