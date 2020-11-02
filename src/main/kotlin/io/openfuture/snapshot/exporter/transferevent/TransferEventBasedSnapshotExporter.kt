package io.openfuture.snapshot.exporter.transferevent

import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.exporter.SnapshotExporter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(name = ["export-strategy"], havingValue = "transfer-event")
class TransferEventBasedSnapshotExporter : SnapshotExporter {
    override fun export(request: ExportSnapshotRequest) {
        //TODO("Not yet implemented")
    }

}
