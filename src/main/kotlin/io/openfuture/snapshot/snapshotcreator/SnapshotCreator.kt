package io.openfuture.snapshot.snapshotcreator

import io.openfuture.snapshot.domain.WalletState

interface SnapshotCreator {
    fun snapshot(contractAddress: String, fromBlock: Int, toBlock: Int): Set<WalletState>
}
