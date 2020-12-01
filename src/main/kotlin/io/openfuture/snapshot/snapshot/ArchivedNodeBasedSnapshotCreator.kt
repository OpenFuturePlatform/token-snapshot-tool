package io.openfuture.snapshot.snapshot

import io.openfuture.snapshot.api.Web3jHelper
import io.openfuture.snapshot.domain.WalletState
import io.openfuture.snapshot.snapshot.SnapshotCreator.Companion.BATCH_SIZE
import org.web3j.utils.Convert

class ArchivedNodeBasedSnapshotCreator(nodeAddress: String) : SnapshotCreator {

    private val web3jHelper = Web3jHelper(nodeAddress)

    override fun snapshot(contractAddress: String, fromBlock: Int, toBlock: Int): List<WalletState> {
        val addresses = fetchAllAddresses(contractAddress, fromBlock, toBlock)

        println("Fetched ${addresses.size} addresses")

        return addresses.map {
            println("Getting balance for $it")
            val balance = web3jHelper.getBalanceOf(contractAddress, it, toBlock)
            val converted = Convert.fromWei(balance, Convert.Unit.GWEI)

            WalletState(it, converted)
        }
    }

    private fun fetchAllAddresses(contractAddress: String, fromBlock: Int, toBlock: Int): Set<String> {
        val addresses = mutableSetOf<String>()

        for (blockNumber in fromBlock..toBlock step BATCH_SIZE) {
            var nextBatch = blockNumber + BATCH_SIZE - 1
            if (nextBatch >= toBlock) nextBatch = toBlock

            println("Batch fetching addresses from $blockNumber to $nextBatch blocks")
            val transfers = web3jHelper.getTransfers(contractAddress, blockNumber, nextBatch)
            val fetchedAddresses = transfers.map { it.fromAddress }

            addresses.addAll(fetchedAddresses)
        }

        return addresses
    }

}
