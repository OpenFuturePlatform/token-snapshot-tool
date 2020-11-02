package io.openfuture.snapshot.runner

import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.exporter.SnapshotExporter
import io.openfuture.snapshot.property.ExportProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class SnapshotRunner(
        private val snapshotExporter: SnapshotExporter,
        private val exportProperties: ExportProperties
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val start = System.currentTimeMillis()

        println("Starting Snapshot snapshot with address ${exportProperties.contractAddress} from block number ${exportProperties.from} to ${exportProperties.to}")

        snapshotExporter.export(toExportSnapshotRequest(exportProperties))

        println("Process time: ${System.currentTimeMillis() - start} millis")

        exitProcess(0)
    }

    private fun toExportSnapshotRequest(exportProperties: ExportProperties): ExportSnapshotRequest {
        return ExportSnapshotRequest(
                exportProperties.contractAddress!!,
                exportProperties.from!!,
                exportProperties.to!!,
                exportProperties.fileName!!,
                exportProperties.contractDecimals!!
        )
    }

}
