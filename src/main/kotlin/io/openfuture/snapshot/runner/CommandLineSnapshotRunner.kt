package io.openfuture.snapshot.runner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.dto.SnapshotRequest
import io.openfuture.snapshot.exporter.CsvSnapshotExporter
import io.openfuture.snapshot.snapshoter.SnapshotFactory
import io.openfuture.snapshot.snapshoter.SnapshotMode
import io.openfuture.snapshot.util.awaitAll
import java.util.concurrent.Executors

class CommandLineSnapshotRunner() : CliktCommand(name = "snapshot") {

    private val contractAddress: String? by option(help = "Address of token Smart contract").required()
    private val decimals: Int by option(help = "Snapshot decimals value").int().default(8)
    private val from: Int? by option(help = "Start block number").int().required()
    private val to: Int? by option(help = "End block number").int().required()
    private val nodeAddress: String? by option(help = "Server url of node to connect").required()
    private val filename: String by option(help = "Name of csv file to save").defaultLazy { "snapshot_at_block_$to.csv" }
    private val snapshotMode: SnapshotMode? by option(help = "Snapshot mode").convert { SnapshotMode.convert(it) }.default(SnapshotMode.ARCHIVED)

    private val exporter = CsvSnapshotExporter()
    private val executor = Executors.newFixedThreadPool(POOL_SIZE)

    override fun run() {
        val snapshoter = SnapshotFactory.getSnapshoter(snapshotMode!!, nodeAddress!!)
        val repository = SnapshotFactory.getSnapshotRepository(snapshotMode!!)

        val toBlockNumber = to ?: snapshoter.getLatestBlockNumber()

        println("Starting Snapshot with address $contractAddress from block number $from to $to")

        for (blockNumber in from!!..toBlockNumber step BATCH_SIZE) {
            var nextBatch = blockNumber + BATCH_SIZE - 1
            if (nextBatch >= toBlockNumber) nextBatch = toBlockNumber

            executor.execute {
                println("Batch snapshot from block $blockNumber to block $nextBatch")

                val request = buildRequest()

                val result = snapshoter.getSnapshot(request)

                repository.saveAll(result)

                println("Added ${result.size} balances from block $nextBatch to block $blockNumber")
            }
        }

        executor.awaitAll()
        val result = repository.getAll()
        exporter.writeResult(filename, result)
    }

    private fun buildRequest(): SnapshotRequest {
        return SnapshotRequest(
                contractAddress!!,
                from!!,
                to!!,
                decimals
        )
    }

    companion object {
        private const val BATCH_SIZE = 20
        private const val POOL_SIZE = 10
    }

}
