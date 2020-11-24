package io.openfuture.snapshot.repository

import io.openfuture.snapshot.dto.ArchivedNodeSnapshotDto
import io.openfuture.snapshot.dto.SnapshotDto

class ArchivedNodeInMemorySnapshotRepository : InMemorySnapshotRepository() {

    override fun saveAll(snapshots: List<SnapshotDto>) {
        snapshots.forEach {
            it as ArchivedNodeSnapshotDto
            val value = snapshotsMap[it.address]
            if (null != value) {
                snapshotsMap.replace(it.address, it.balance)
            } else {
                snapshotsMap[it.address] = it.balance
            }
        }
    }

}
