
package io.openfuture.snapshot.snapshoter

import io.openfuture.snapshot.repository.ArchivedNodeInMemorySnapshotRepository
import io.openfuture.snapshot.repository.SnapshotRepository
import io.openfuture.snapshot.repository.TransferEventInMemorySnapshotRepository
import io.openfuture.snapshot.snapshoter.SnapshotMode.ARCHIVED
import io.openfuture.snapshot.snapshoter.SnapshotMode.EVENT

class SnapshotFactory {

    companion object {

        fun getSnapshoter(snapshotMode: SnapshotMode, nodeAddress: String): Snapshoter {

            if (ARCHIVED == snapshotMode) return ArchivedNodeBasedSnapshoter(nodeAddress)
            if (EVENT == snapshotMode) return TransferEventBasedSnapshoter(nodeAddress)

            throw IllegalArgumentException("Not implemented!")
        }

        fun getSnapshotRepository(snapshotMode: SnapshotMode): SnapshotRepository {
            if (ARCHIVED == snapshotMode) return ArchivedNodeInMemorySnapshotRepository()
            if (EVENT == snapshotMode) return TransferEventInMemorySnapshotRepository()

            throw IllegalArgumentException("Not implemented!")
        }

    }
}
