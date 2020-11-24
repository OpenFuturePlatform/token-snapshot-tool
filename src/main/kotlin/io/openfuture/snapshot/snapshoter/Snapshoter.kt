package io.openfuture.snapshot.snapshoter

import io.openfuture.snapshot.dto.SnapshotDto
import io.openfuture.snapshot.dto.SnapshotRequest
import org.web3j.abi.EventEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.exceptions.ClientConnectionException
import org.web3j.protocol.http.HttpService
import java.math.BigDecimal
import kotlin.math.pow

abstract class Snapshoter(nodeAddress: String) {

    protected val web3j: Web3j = Web3j.build(HttpService(nodeAddress))

    fun getSnapshot(request: SnapshotRequest): List<SnapshotDto> {
        return try {
            val result = snapshot(request.fromBlock, request.toBlock, request.contractAddress)
            result.map {
                it.balance = BigDecimal(it.balance.toDouble() * 10.0.pow(-request.decimals.toDouble()))
                it
            }
        } catch (ex: ClientConnectionException) {
            getSnapshot(request)
        }
    }

    protected abstract fun snapshot(from: Int, to: Int, contractAddress: String): List<SnapshotDto>

    fun getLatestBlockNumber(): Int = web3j.ethBlockNumber().send().blockNumber.toInt()

    protected fun getLogs(contractAddress: String, fromBlock: Int, toBlock: Int): MutableList<EthLog.LogResult<Any>> {
        val transferFilter = createTransferFilter(contractAddress, fromBlock, toBlock)
        return web3j.ethGetLogs(transferFilter).send().logs
    }

    protected fun decodeAddress(rawData: String) =
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

    companion object {
        const val TRANSFER_EVENT = "Transfer"
        const val BALANCE_METHOD = "balanceOf"
    }

}
