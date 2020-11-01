package io.openfuture.snapshot.service

import io.openfuture.snapshot.dto.SnapshotRequest
import io.openfuture.snapshot.property.Properties
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.io.File
import java.io.PrintWriter
import kotlin.system.exitProcess

@Component
class SnapshotRunner(
        private val snapshot: SnapshotService,
        private val properties: Properties
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val start = System.currentTimeMillis()

        println("Starting Snapshot snapshot with address ${properties.contractAddress} from block number ${properties.from} to ${properties.to}")

        snapshot.batchSnapshotToCsvFile(toSnapshotRequest(properties))

        checkDuplicateAddresses()

        println("Process time: ${System.currentTimeMillis() - start} millis")

        exitProcess(0)
    }

    private fun toSnapshotRequest(properties: Properties): SnapshotRequest {
        return SnapshotRequest(
                properties.contractAddress!!,
                properties.from!!,
                properties.to!!,
                properties.fileName!!
        )
    }

    private fun checkDuplicateAddresses() {
        val balances = read(properties.fileName!!)
        writeResult(balances)
    }

    private fun read(path: String): Set<List<String>> {
        val lines = File("${System.getProperty("user.dir")}/$path").readLines()

        return lines.map {
            it.split(",")
        }.toSet()
    }

    private fun writeResult(results: Set<List<String?>>) {
        val writer = PrintWriter("clear_${properties.fileName}", "UTF-8")
        results.forEach { writer.println("${it[0]},${it[1]}") }
        writer.flush()
        writer.close()
    }

}
