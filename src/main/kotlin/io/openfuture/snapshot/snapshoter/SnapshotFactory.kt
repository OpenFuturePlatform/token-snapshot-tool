
package io.openfuture.snapshot.snapshoter

import io.openfuture.snapshot.snapshoter.SnapshotMode.*
import io.openfuture.snapshot.repository.ArchivedNodeInMemorySnapshotRepository
import io.openfuture.snapshot.repository.InMemorySnapshotRepository
import java.lang.IllegalArgumentException

class SnapshotFactory {

    companion object {

        fun getSnapshoter(snapshotMode: SnapshotMode, nodeAddress: String): Snapshoter {

            if (ARCHIVED == snapshotMode) return ArchivedNodeBasedSnapshoter(nodeAddress)

            throw IllegalArgumentException("Not implemented!")
        }

        fun getSnapshotRepository(snapshotMode: SnapshotMode): InMemorySnapshotRepository {
            if (ARCHIVED == snapshotMode) return ArchivedNodeInMemorySnapshotRepository()

            throw IllegalArgumentException("Not implemented!")
        }

    }
}
