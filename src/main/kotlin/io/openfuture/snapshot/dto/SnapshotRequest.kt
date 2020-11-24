package io.openfuture.snapshot.dto

data class SnapshotRequest(
        val contractAddress: String,
        val fromBlock: Int,
        val toBlock: Int,
        val decimals: Int
)
