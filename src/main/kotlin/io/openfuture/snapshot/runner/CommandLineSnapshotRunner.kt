package io.openfuture.snapshot.runner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.resolver.ExportStrategyResolver
import io.openfuture.snapshot.exporter.SnapshotStrategy
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class CommandLineSnapshotRunner(private val exportStrategyResolver: ExportStrategyResolver) : CommandLineRunner, CliktCommand(name = "snapshot") {

    private val contractAddress: String? by option(help = "Address of token Smart contract").required()
    private val decimals: Int by option(help = "Snapshot decimals value").int().default(8)
    private val from: Int? by option(help = "Start block number").int().required()
    private val to: Int? by option(help = "End block number").int().required()
    private val nodeAddress: String? by option(help = "Server url of node to connect").required()
    private val filename: String by option(help = "Name of csv file to save").defaultLazy { "snapshot_at_block_$to.csv" }
    private val strategy: SnapshotStrategy? by option(help = "Snapshot strategy").convert { SnapshotStrategy.convert(it) }.default(SnapshotStrategy.ARCHIVED)

    override fun run(vararg args: String?) {
        super.main(args.map { it.toString() }.toList())
    }

    override fun run() {
        val start = System.currentTimeMillis()

        println("Starting Snapshot with address $contractAddress from block number $from to $to")

        val exporter = exportStrategyResolver.resolve(strategy!!)

        exporter.export(buildExportSnapshotRequest())

        println("Process time: ${System.currentTimeMillis() - start} millis")

        exitProcess(0)
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
