package io.openfuture.snapshot.resolver

import io.openfuture.snapshot.exporter.SnapshotExporter
import io.openfuture.snapshot.exporter.SnapshotStrategy
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class ExportStrategyResolver(private val exporters: List<SnapshotExporter>) {

    fun resolve(strategy: SnapshotStrategy): SnapshotExporter {
        return exporters.find { it.strategy() == strategy } ?: throw IllegalArgumentException()
    }
}
