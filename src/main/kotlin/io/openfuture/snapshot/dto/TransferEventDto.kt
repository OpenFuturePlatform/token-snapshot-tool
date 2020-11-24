package io.openfuture.snapshot.dto

import java.math.BigDecimal

data class TransferEventDto(
        val fromAddress: String,
        val toAddress: String,
        var amount: BigDecimal
) : SnapshotDto(amount)
