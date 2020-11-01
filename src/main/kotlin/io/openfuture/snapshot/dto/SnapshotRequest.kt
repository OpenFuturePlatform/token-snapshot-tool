package io.openfuture.snapshot.dto

data class SnapshotRequest(
        val address: String,
        val fromBlock: Int,
        val toBlock: Int,
        val fileName: String
)
