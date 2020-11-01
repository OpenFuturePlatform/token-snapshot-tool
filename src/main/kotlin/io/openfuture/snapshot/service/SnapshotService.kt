package io.openfuture.snapshot.service

import io.openfuture.snapshot.component.Web3jWrapper
import io.openfuture.snapshot.dto.SnapshotRequest
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import org.web3j.abi.datatypes.Address
import java.io.File
import java.io.PrintWriter
import java.math.BigDecimal

@Service
class SnapshotService(
        private val web3j: Web3jWrapper,
        private val executor: ThreadPoolTaskExecutor
) {

    fun batchSnapshotToCsvFile(snapshotRequest: SnapshotRequest) {

        val writer = PrintWriter(snapshotRequest.fileName, "UTF-8")

        writer.println(HEADER)

        for (blockNumber in snapshotRequest.toBlock downTo snapshotRequest.fromBlock step BATCH_SIZE) {
            var nextBatch = blockNumber - BATCH_SIZE + 1
            if (nextBatch <= snapshotRequest.fromBlock) nextBatch = snapshotRequest.fromBlock

            executor.execute {
                println("Batch snapshot from block $blockNumber to block $nextBatch")

                val addresses = web3j.getAddressesFromTransferEvents(snapshotRequest.address, nextBatch, blockNumber)
                println("Fetched addresses ${addresses.size}")

                val balances = getBalancesAtBlock(addresses, snapshotRequest.address, snapshotRequest.toBlock)

                writeResult(balances, writer)

                println("Added ${balances.size} balances from block $nextBatch to block $blockNumber")
            }
        }

        executor.shutdown()
        while (!executor.threadPoolExecutor.isTerminating) {
        }

        writer.close()
    }

    private fun getBalancesAtBlock(addresses: Set<Address>, tokenAddress: String, blockNumber: Int): Map<String, BigDecimal> {
        return addresses
                .map {
                    val balance = web3j.getTokenBalanceAtBlock(it.value, tokenAddress, blockNumber)
                    it.value to balance.toBigDecimal()
                }
                .toMap()
    }

    private fun writeResult(results: Map<String, BigDecimal>, writer: PrintWriter) {
        results.forEach { writer.println("${it.key},${it.value}") }
        writer.flush()
    }

    companion object {
        private const val BATCH_SIZE = 10
        const val HEADER = "ADDRESS,BALANCE"
    }

}
