package io.openfuture.snapshot

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.CommandLineSnapshotRunner.Mode.*
import io.openfuture.snapshot.exporter.CsvSnapshotExporter
import io.openfuture.snapshot.exporter.FileExporter
import io.openfuture.snapshot.snapshot.ArchivedNodeBasedSnapshotCreator
import io.openfuture.snapshot.snapshot.SnapshotCreator
import io.openfuture.snapshot.snapshot.TransferEventBasedSnapshotCreator
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class CommandLineSnapshotRunner : CliktCommand(name = "snapshot") {

    private val contractAddress: String by option(help = "Address of token Smart contract", names = arrayOf("-c", "--contract")).required()
    private val from: Int by option(help = "Start block number", names = arrayOf("-f", "--from")).int().default(0)
    private val to: Int? by option(help = "End block number", names = arrayOf("-t", "--to")).int()
    private val nodeAddress: String by option(help = "Server url of node to connect", names = arrayOf("-n", "--node-address")).required()
    private val filename: String by option(help = "Name of csv file to save", names = arrayOf("-o", "--output")).defaultLazy { "snapshot_at_block_$to.csv" }
    private val mode: Mode by option(help = "Snapshot mode", names = arrayOf("-m", "--mode")).convert { Mode.valueOf(it.toUpperCase()) }.default(EVENT)

    private val exporter: FileExporter = CsvSnapshotExporter()

    override fun run() {
        val creator: SnapshotCreator = when (mode) {
            ARCHIVED -> ArchivedNodeBasedSnapshotCreator(nodeAddress)
            EVENT -> TransferEventBasedSnapshotCreator(nodeAddress)
        }

        val toBlockNumber = to ?: Web3jHelper(nodeAddress).getLatestBlock()
        val results = creator.snapshot(contractAddress, from, toBlockNumber)
        exporter.export(filename, results)
    }

    private enum class Mode {
        ARCHIVED,
        EVENT
    }

    class Web3jHelper(nodeAddress: String) {

        private val web3j: Web3j = Web3j.build(HttpService(nodeAddress))

        fun getLatestBlock(): Int = web3j.ethBlockNumber().send().blockNumber.toInt()
    }

}

fun main(args: Array<String>) {
    CommandLineSnapshotRunner().main(args)
}
