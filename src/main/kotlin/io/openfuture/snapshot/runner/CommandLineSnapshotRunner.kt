package io.openfuture.snapshot.runner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.exporter.SnapshotExporter

class CommandLineSnapshotRunner(private val snapshotExporter: SnapshotExporter) : CliktCommand(name = "snapshot") {

    private val contractAddress: String? by option(help = "Address of token Smart contract").required()
    private val decimals: Int by option(help = "Snapshot decimals value").int().default(8)
    private val from: Int? by option(help = "Start block number").int().required()
    private val to: Int? by option(help = "End block number").int().required()
    private val nodeAddress: String? by option(help = "Server url of node to connect").required()
    private val filename: String by option(help = "Name of csv file to save").defaultLazy { "snapshot_at_block_$to.csv" }

    override fun run() {
        val start = System.currentTimeMillis()

        println("Starting Snapshot with address $contractAddress from block number $from to $to")

        snapshotExporter.export(buildExportSnapshotRequest())

        println("Process time: ${System.currentTimeMillis() - start} millis")
    }

    private fun buildExportSnapshotRequest(): ExportSnapshotRequest {
        return ExportSnapshotRequest(
                contractAddress!!,
                from!!,
                to!!,
                filename,
                decimals,
                nodeAddress!!
        )
    }

}
