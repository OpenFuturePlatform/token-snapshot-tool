package io.openfuture.snapshot.service

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.component.Web3jWrapper
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter

/**
 * @author Igor Pahomov
 */
@Component
class DefaultCommandLine(private val snapshot: SnapshotService,
                         private val web3j: Web3jWrapper
) : CliktCommand(), CommandLine {

    override fun start(args: Array<out String?>) {
        super.main(args.map { it.toString() }.toList())
    }

    private val address: String? by option(help = "Address of token Smart contract")
    private val decimals: Int by option(help = "Snapshot decimals value").int().default(18)
    private val from: Int? by option(help = "Start block number").int()
    private val to: Int? by option(help = "End block number").int()
    private val server: String? by option(help = "Server url of node connected to")
    private val filename: String by option(help = "Name of csv file to save").defaultLazy { "snapshot_at_block_$to.csv" }


    override fun run() {
        println("Starting Snapshot snapshot with address $address from block number $from to $to")

        web3j.init(server)
//        snapshot.batchSnapshotToCsvFile(Snapshot(address, decimals, from, to), filename)

        val tokenSnapshot = TokenSnapshot(address, from!!, to!!, decimals, 8, server!!)
        tokenSnapshot.getBatchSnapshotToCsv()

        checkDublicateAddresses()
    }

    private fun checkDublicateAddresses() {
        val balances = read(filename)
        writeResult(balances)
    }

    private fun read(path: String): Set<List<String>> {
        val lines = File("${System.getProperty("user.dir")}/$path").readLines()

        return lines.map {
            it.split(",")
        }.toSet()
    }

    private fun writeResult(results: Set<List<String?>>) {
        val writer = PrintWriter("clear_$filename", "UTF-8")
        writer.println(TokenSnapshot.HEADER)
        results.forEach { writer.println("${it[0]},${it[1]}") }
        writer.flush()
        writer.close()
    }

}
