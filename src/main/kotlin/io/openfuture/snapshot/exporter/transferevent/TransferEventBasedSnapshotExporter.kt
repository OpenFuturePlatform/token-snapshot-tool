package io.openfuture.snapshot.exporter.transferevent

import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.exporter.SnapshotExporter
import io.openfuture.snapshot.exporter.SnapshotStrategy
import org.springframework.stereotype.Component

@Component
class TransferEventBasedSnapshotExporter : SnapshotExporter {
    override fun export(request: ExportSnapshotRequest) {
        //TODO("Not yet implemented")
    }

    override fun strategy(): SnapshotStrategy = SnapshotStrategy.TRANSFER_EVENT

}
