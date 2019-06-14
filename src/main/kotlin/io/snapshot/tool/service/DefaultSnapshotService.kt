package io.snapshot.tool.service

import io.snapshot.tool.component.Web3jWrapper
import io.snapshot.tool.model.Snapshot
import org.springframework.stereotype.Service
import org.web3j.abi.datatypes.Address
import java.io.PrintWriter
import java.math.BigDecimal
import java.util.concurrent.Executors

/**
 * @author Igor Pahomov
 */
@Service
class DefaultSnapshotService(
        private val web3j: Web3jWrapper
) : SnapshotService {

    private val executor = Executors.newFixedThreadPool(POOL_SIZE)


    override fun batchSnapshotToCsvFile(snapshot: Snapshot, fileName: String) {
        val start = System.currentTimeMillis()

        val writer = PrintWriter(fileName, "UTF-8")

        writer.println(HEADER)

        for (blockNumber in snapshot.toBlock!! downTo snapshot.fromBlock!! step BATCH_SIZE) {
            var nextBatch = blockNumber - BATCH_SIZE + 1
            if (nextBatch <= snapshot.fromBlock) nextBatch = snapshot.fromBlock

            executor.execute {
                println("Batch snapshot from block $blockNumber to block $nextBatch")

                val addresses = web3j.getAddressesFromTransferEvents(snapshot.address, nextBatch, blockNumber)
                println("Fetched addresses ${addresses.size}")

                val balances = getBalancesAtBlock(addresses, snapshot.address, snapshot.toBlock, snapshot.decimals)

                writeResult(balances, writer)

                println("Added ${balances.size} balances from block $nextBatch to block $blockNumber")
            }
        }

        executor.shutdown()
        while (!executor.isTerminated) {
        }

        writer.close()
        println("Process time: ${System.currentTimeMillis() - start} millis")
    }

    private fun getBalancesAtBlock(addresses: Set<Address>, tokenAddress: String?, blockNumber: Int, decimals: Int?): Map<String, BigDecimal> {
        return addresses
                .map {
                    val balance = web3j.getTokenBalanceAtBlock(it.value, tokenAddress, blockNumber)
                    it.value to BigDecimal(balance.toDouble() * Math.pow(10.0, -decimals?.toDouble()!!))
                }
                .toMap()
    }

    private fun writeResult(results: Map<String, BigDecimal>, writer: PrintWriter) {
        results.forEach { writer.println("${it.key},${it.value}") }
        writer.flush()
    }

    companion object {
        private const val BATCH_SIZE = 10
        private const val POOL_SIZE = 10
        private const val HEADER = "ADDRESS,BALANCE"
    }

}