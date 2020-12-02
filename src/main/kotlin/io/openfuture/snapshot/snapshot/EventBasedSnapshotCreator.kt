package io.openfuture.snapshot.snapshot

import io.openfuture.snapshot.api.Transfer
import io.openfuture.snapshot.api.Web3jHelper
import io.openfuture.snapshot.domain.WalletState
import io.openfuture.snapshot.snapshot.SnapshotCreator.Companion.BATCH_SIZE
import java.math.BigDecimal

class EventBasedSnapshotCreator(nodeAddress: String) : SnapshotCreator {

    private val web3jHelper = Web3jHelper(nodeAddress)

    override fun snapshot(contractAddress: String, fromBlock: Int, toBlock: Int): List<WalletState> {
        val states = mutableMapOf<String, BigDecimal>()

        for (blockNumber in fromBlock..toBlock step BATCH_SIZE) {
            var nextBatch = blockNumber + BATCH_SIZE - 1
            if (nextBatch >= toBlock) nextBatch = toBlock

            println("Batch fetching addresses from $blockNumber to $nextBatch blocks")
            val transfers = web3jHelper.getTransfers(contractAddress, blockNumber, nextBatch)
            calculateBalances(states, transfers)
        }

        return states.map { WalletState(it.key, it.value) }
    }

    private fun calculateBalances(walletStateMap: MutableMap<String, BigDecimal>, transfers: List<Transfer>) {
        for (transfer in transfers) {
            // to
            val toAmount = walletStateMap.getOrDefault(transfer.toAddress, BigDecimal.ZERO)
            walletStateMap[transfer.toAddress] = toAmount.plus(transfer.amount)

            // from
            if (transfer.fromAddress == Web3jHelper.GENESIS_ADDRESS) {
                continue
            }

            val fromAmount = walletStateMap.getOrDefault(transfer.fromAddress, BigDecimal.ZERO)
            walletStateMap[transfer.fromAddress] = fromAmount.minus(transfer.amount)
        }
    }

}
