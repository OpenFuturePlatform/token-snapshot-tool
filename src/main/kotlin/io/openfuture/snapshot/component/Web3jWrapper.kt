package io.openfuture.snapshot.component

import org.springframework.stereotype.Component
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
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.exceptions.ClientConnectionException
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract
import org.web3j.tx.ManagedTransaction
import java.math.BigInteger

/**
 * @author Igor Pahomov
 */
@Component
class Web3jWrapper(private var web3j: Web3j
) {

    companion object {
        private const val TRANSFER_EVENT = "Transfer"
        private const val BALANCE_METHOD = "balanceOf"
        private const val ERROR_TRANSACTION_STATUS = "0x0"
        private const val POOL_SIZE = 10
    }

    private var server: String? = null

    fun init(server: String?) {
        this.server = server
        this.web3j = Web3j.build(HttpService(server))
    }

    fun getAddressesFromTransferEvents(tokenAddress: String?, fromBlock: Int, toBlock: Int): Set<Address> {
        var ethLog = EthLog()
        var disconnect = false
        try {
            ethLog = web3j.ethGetLogs(createTransferFilter(tokenAddress, fromBlock, toBlock)).send()
        } catch (e: ClientConnectionException) {
            disconnect = true
        } finally {
            if (disconnect) {
                println("Disconnected!")
                web3j = Web3j.build(HttpService(server))
                getAddressesFromTransferEvents(tokenAddress, fromBlock, toBlock)
            }
        }

        val ethTransferLogs: MutableList<EthLog.LogResult<Any>> = ethLog.logs

        return fetchAddressesFromLogs(ethTransferLogs)
    }

    fun getTokenBalanceAtBlock(address: String, tokenAddress: String?, blockNumber: Int): BigInteger {
        val function = Function(BALANCE_METHOD, listOf(Address(address)), listOf(object : TypeReference<Uint256>() {}))
        val encodedFunction = FunctionEncoder.encode(function)
        val result = web3j.ethCall(
                Transaction.createFunctionCallTransaction(null, null, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, tokenAddress, encodedFunction),
                DefaultBlockParameter.valueOf(blockNumber.toBigInteger()))
                .send()

        return FunctionReturnDecoder.decode(result.value, function.outputParameters).first().value as BigInteger
    }

    private fun createTransferFilter(address: String?, fromBlock: Int, toBlock: Int): EthFilter {
        return EthFilter(
                DefaultBlockParameter.valueOf(fromBlock.toBigInteger()),
                DefaultBlockParameter.valueOf(toBlock.toBigInteger()),
                address
        )
                .addSingleTopic(EventEncoder.encode(
                        Event(
                                TRANSFER_EVENT,
                                listOf(object : TypeReference<Address>() {}, object : TypeReference<Address>() {}) as List<TypeReference<*>>?,
                                listOf(object : TypeReference<Uint256>() {}) as List<TypeReference<*>>?
                        )))
    }

    private fun fetchAddressesFromLogs(transferLogs: List<EthLog.LogResult<Any>>): Set<Address> {
        return transferLogs.map {
            val topics: List<String> = (it.get() as EthLog.LogObject).topics

            // get recipient address
            decodeAddress(topics[2])
        }.toSet()
    }

    private fun decodeAddress(rawData: String) =
            FunctionReturnDecoder.decodeIndexedValue(rawData, object : TypeReference<Address>() {}) as Address

}