package io.openfuture.snapshot.exporter

import io.openfuture.snapshot.dto.ExportSnapshotRequest

interface SnapshotExporter {
    fun export(request: ExportSnapshotRequest)
}
