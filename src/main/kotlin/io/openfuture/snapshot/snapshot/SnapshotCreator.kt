package io.openfuture.snapshot.snapshot

import io.openfuture.snapshot.domain.WalletState

interface SnapshotCreator {

    fun snapshot(contractAddress: String, fromBlock: Int, toBlock: Int): List<WalletState>

    companion object {
        const val BATCH_SIZE = 20
    }

}
