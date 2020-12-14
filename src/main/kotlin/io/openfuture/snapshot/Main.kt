package io.openfuture.snapshot

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.CommandLineSnapshotRunner.Mode.ARCHIVED
import io.openfuture.snapshot.CommandLineSnapshotRunner.Mode.EVENT
import io.openfuture.snapshot.api.Web3jHelper
import io.openfuture.snapshot.exporter.CsvSnapshotExporter
import io.openfuture.snapshot.exporter.FileExporter
import io.openfuture.snapshot.snapshot.ArchivedNodeBasedSnapshotCreator
import io.openfuture.snapshot.snapshot.EventBasedSnapshotCreator
import io.openfuture.snapshot.snapshot.SnapshotCreator

class CommandLineSnapshotRunner : CliktCommand(name = "token-snapshot") {

    private val nodeAddress: String by option("-n", "--node-address", help = "Server url of node to connect")
            .required()

    private val contractAddress: String by option("-c", "--contract", help = "Address of token Smart contract")
            .required()

    private val from: Int by option("-f", "--from", help = "Start block number")
            .int()
            .default(0)

    private val to: Int? by option("-t", "--to", help = "End block number")
            .int()

    private val filename: String by option("-o", "--output", help = "Name of csv file to save")
            .defaultLazy { "snapshot_at_block_$to.csv" }

    private val mode: Mode by option("-m", "--mode", help = "Snapshot mode")
            .convert { Mode.valueOf(it.toUpperCase()) }
            .default(EVENT)


    private val exporter: FileExporter = CsvSnapshotExporter()

    override fun run() {
        val creator: SnapshotCreator = when (mode) {
            ARCHIVED -> ArchivedNodeBasedSnapshotCreator(nodeAddress)
            EVENT -> EventBasedSnapshotCreator(nodeAddress)
        }

        val toBlockNumber = to ?: Web3jHelper(nodeAddress).getLatestBlock()
        val results = creator.snapshot(contractAddress, from, toBlockNumber)
        exporter.export(filename, results)
    }

    private enum class Mode {
        ARCHIVED, EVENT
    }

}

fun main(args: Array<String>) {
    CommandLineSnapshotRunner().main(args)
}
