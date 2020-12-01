package io.openfuture.snapshot.api

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

class Web3jHelper(nodeAddress: String) {

    private val web3j: Web3j = Web3j.build(HttpService(nodeAddress))

    fun getLatestBlock(): Int = web3j.ethBlockNumber().send().blockNumber.toInt()

    fun getTransfers(address: String, fromBlock: Int, toBlock: Int): List<Transfer> {
        val logs = getLogs(TRANSFER_EVENT, address, fromBlock, toBlock)
        return fetchTransfersFromLogs(logs)
    }

    fun getBalanceOf(tokenAddress: String, walletAddress: String, blockNumber: Int): BigDecimal {
        return try {
            val function = Function(
                    BALANCE_METHOD,
                    listOf(Address(walletAddress)),
                    listOf(object : TypeReference<Uint256>() {})
            )
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
            getBalanceOf(tokenAddress, walletAddress, blockNumber)
        }
    }

    private fun getLogs(event: String, address: String, fromBlock: Int, toBlock: Int): List<EthLog.LogResult<Any>> {
        return try {
            val filter = createFilter(address, event, fromBlock, toBlock)
            web3j.ethGetLogs(filter).send().logs
        } catch (ex: ClientConnectionException) {
            getLogs(address, event, fromBlock, toBlock)
        }
    }

    private fun decodeAddress(rawData: String): Address =
            FunctionReturnDecoder.decodeIndexedValue(rawData, object : TypeReference<Address>() {}) as Address

    private fun createFilter(address: String, event: String, fromBlock: Int, toBlock: Int): EthFilter {
        return EthFilter(
                DefaultBlockParameter.valueOf(fromBlock.toBigInteger()),
                DefaultBlockParameter.valueOf(toBlock.toBigInteger()),
                address
        ).addSingleTopic(EventEncoder.encode(createTransferEvent(event)))
    }

    private fun createTransferEvent(event: String) = Event(
            event,
            listOf(
                    object : TypeReference<Address>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Uint256>() {}
            )
    )

    private fun fetchTransfersFromLogs(logs: List<EthLog.LogResult<Any>>): List<Transfer> {
        return logs.map {
            val log = (it.get() as EthLog.LogObject)
            val topics: List<String> = log.topics

            val fromAddress = decodeAddress(topics[1]).value
            val toAddress = decodeAddress(topics[2]).value
            val amount = BigDecimal(BigInteger(log.data.substring(2), 16))
            val converted = Convert.fromWei(amount, Convert.Unit.GWEI)

            Transfer(fromAddress, toAddress, converted)
        }
    }

    companion object {
        const val GENESIS_ADDRESS = "0x0000000000000000000000000000000000000000"

        const val BALANCE_METHOD = "balanceOf"
        const val TRANSFER_EVENT = "Transfer"
    }

}
