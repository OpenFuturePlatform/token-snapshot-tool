package io.openfuture.snapshot.snapshot

import io.openfuture.snapshot.domain.WalletState
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.exceptions.ClientConnectionException
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

class ArchivedNodeBasedSnapshotCreator(nodeAddress: String) : BaseSnapshotCreator(nodeAddress) {

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
                    Transaction.createFunctionCallTransaction(
                            null,
                            null,
                            DefaultGasProvider.GAS_PRICE,
                            DefaultGasProvider.GAS_LIMIT,
                            tokenAddress,
                            encodedFunction
                    ),
                    DefaultBlockParameter.valueOf(blockNumber.toBigInteger()))
                    .send()

            (FunctionReturnDecoder.decode(result.value, function.outputParameters).first().value as BigInteger).toBigDecimal()
        } catch (ex: ClientConnectionException) {
            getTokenBalanceAtBlock(address, tokenAddress, blockNumber)
        }
    }

    companion object {
        const val BALANCE_METHOD = "balanceOf"
    }

}
