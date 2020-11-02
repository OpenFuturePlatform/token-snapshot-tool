package io.openfuture.snapshot.runner

import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.exporter.SnapshotExporter
import io.openfuture.snapshot.property.Properties
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class SnapshotRunner(
        private val snapshotExporter: SnapshotExporter,
        private val properties: Properties
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val start = System.currentTimeMillis()

        println("Starting Snapshot snapshot with address ${properties.contractAddress} from block number ${properties.from} to ${properties.to}")

        snapshotExporter.export(toExportSnapshotRequest(properties))

        println("Process time: ${System.currentTimeMillis() - start} millis")

        exitProcess(0)
    }

    private fun toExportSnapshotRequest(properties: Properties): ExportSnapshotRequest {
        return ExportSnapshotRequest(
                properties.contractAddress!!,
                properties.from!!,
                properties.to!!,
                properties.fileName!!,
                properties.decimals!!
        )
    }

}
