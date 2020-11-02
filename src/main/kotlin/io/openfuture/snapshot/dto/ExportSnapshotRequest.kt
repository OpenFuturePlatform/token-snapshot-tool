package io.openfuture.snapshot.dto

import java.math.BigInteger

data class ExportSnapshotRequest(
        val address: String,
        val fromBlock: Int,
        val toBlock: Int,
        val fileName: String,
        val decimals: BigInteger
)
