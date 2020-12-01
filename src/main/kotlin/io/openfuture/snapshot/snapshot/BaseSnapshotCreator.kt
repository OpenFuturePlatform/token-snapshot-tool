package io.openfuture.snapshot.snapshot

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

abstract class BaseSnapshotCreator(nodeAddress: String) : SnapshotCreator {

    protected val web3j: Web3j = Web3j.build(HttpService(nodeAddress))

    protected fun getTransferLogs(contractAddress: String, fromBlock: Int, toBlock: Int): List<EthLog.LogResult<Any>> {
        return try {
            val transferFilter = createTransferFilter(contractAddress, fromBlock, toBlock)
            web3j.ethGetLogs(transferFilter).send().logs
        } catch (ex: ClientConnectionException) {
            getTransferLogs(contractAddress, fromBlock, toBlock)
        }
    }

    protected fun decodeAddress(rawData: String): Address = FunctionReturnDecoder.decodeIndexedValue(
            rawData,
            object : TypeReference<Address>() {}
    ) as Address

    private fun createTransferFilter(contractAddress: String, fromBlock: Int, toBlock: Int): EthFilter {
        return EthFilter(
                DefaultBlockParameter.valueOf(fromBlock.toBigInteger()),
                DefaultBlockParameter.valueOf(toBlock.toBigInteger()),
                contractAddress
        ).addSingleTopic(EventEncoder.encode(createTransferEvent()))
    }

    private fun createTransferEvent() = Event(
            TRANSFER_EVENT,
            listOf(
                    object : TypeReference<Address>() {},
                    object : TypeReference<Address>() {},
                    object : TypeReference<Uint256>() {}
            )
    )

    companion object {
        const val BATCH_SIZE = 20
        const val TRANSFER_EVENT = "Transfer"
    }

}
