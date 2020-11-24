package io.openfuture.snapshot.snapshoter

import io.openfuture.snapshot.dto.SnapshotDto
import io.openfuture.snapshot.dto.TransferEventDto
import org.web3j.protocol.core.methods.response.EthLog
import java.math.BigDecimal
import java.math.BigInteger

class TransferEventBasedSnapshoter(nodeAddress: String) : Snapshoter(nodeAddress) {

    override fun snapshot(from: Int, to: Int, contractAddress: String): List<SnapshotDto> {
        val ethTransferLogs: MutableList<EthLog.LogResult<Any>> = getLogs(contractAddress, from, to)

        return fetchAddressesFromLogs(ethTransferLogs)
    }

    private fun fetchAddressesFromLogs(transferLogs: List<EthLog.LogResult<Any>>): List<SnapshotDto> {
        return transferLogs.map {
            val log = (it.get() as EthLog.LogObject)
            val topics: List<String> = log.topics

            val fromAddress = decodeAddress(topics[1]).value
            val toAddress = decodeAddress(topics[2]).value
            val amount = BigDecimal(BigInteger(log.data.substring(2), 16))

            TransferEventDto(fromAddress, toAddress, amount)
        }.toList()
    }

}
