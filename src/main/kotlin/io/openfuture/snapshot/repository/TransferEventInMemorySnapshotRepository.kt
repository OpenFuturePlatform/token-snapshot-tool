package io.openfuture.snapshot.repository

import io.openfuture.snapshot.dto.SnapshotDto
import io.openfuture.snapshot.dto.TransferEventDto

class TransferEventInMemorySnapshotRepository : InMemorySnapshotRepository() {
    override fun saveAll(snapshots: List<SnapshotDto>) {
        snapshots.forEach {
            it as TransferEventDto
            val toAmount = snapshotsMap[it.toAddress]

            if (null != toAmount) {
                snapshotsMap.replace(it.toAddress, toAmount.plus(it.amount))
            } else {
                snapshotsMap[it.toAddress] = it.amount
            }

            if ("0x0000000000000000000000000000000000000000" == it.fromAddress) {
                return
            }

            val fromAmount = snapshotsMap[it.fromAddress]
            if (null != fromAmount) {
                snapshotsMap.replace(it.fromAddress, fromAmount.minus(it.amount))
                fromAmount.unaryMinus()
            } else {
                snapshotsMap[it.fromAddress] = it.amount.unaryMinus()
            }

        }
    }
}
