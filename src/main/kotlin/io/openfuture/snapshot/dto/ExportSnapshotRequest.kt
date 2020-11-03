package io.openfuture.snapshot.dto

data class ExportSnapshotRequest(
        val address: String,
        val fromBlock: Int,
        val toBlock: Int,
        val fileName: String,
        val decimals: Int,
        val nodeAddress: String
)
