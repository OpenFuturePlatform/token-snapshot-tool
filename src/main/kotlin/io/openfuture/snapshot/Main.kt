package io.openfuture.snapshot

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.CommandLineSnapshotRunner.Mode.ARCHIVED
import io.openfuture.snapshot.CommandLineSnapshotRunner.Mode.valueOf
import io.openfuture.snapshot.exporter.CsvSnapshotExporter
import io.openfuture.snapshot.exporter.FileExporter
import io.openfuture.snapshot.snapshot.ArchivedNodeBasedSnapshotCreator
import io.openfuture.snapshot.snapshot.SnapshotCreator

class CommandLineSnapshotRunner : CliktCommand(name = "snapshot") {

    private val contractAddress: String by option(help = "Address of token Smart contract").required()
    private val from: Int by option(help = "Start block number").int().required()
    private val to: Int by option(help = "End block number").int().required()
    private val nodeAddress: String by option(help = "Server url of node to connect").required()
    private val filename: String by option(help = "Name of csv file to save").defaultLazy { "snapshot_at_block_$to.csv" }
    private val mode: Mode by option(help = "Snapshot mode").convert { valueOf(it) }.default(ARCHIVED)

    private val exporter: FileExporter = CsvSnapshotExporter()

    override fun run() {
        val creator: SnapshotCreator = when (mode) {
            ARCHIVED -> ArchivedNodeBasedSnapshotCreator(nodeAddress)
        }

        val results = creator.snapshot(contractAddress, from, to)
        exporter.export(filename, results)
    }

    private enum class Mode {
        ARCHIVED;
    }

}

fun main(args: Array<String>) {
    CommandLineSnapshotRunner().main(args)
}
