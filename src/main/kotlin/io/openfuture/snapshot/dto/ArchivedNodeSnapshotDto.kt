package io.openfuture.snapshot.dto

import java.math.BigDecimal

data class ArchivedNodeSnapshotDto(
        val address: String,
        override var balance: BigDecimal
) : SnapshotDto(balance)
