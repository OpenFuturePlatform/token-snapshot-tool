package io.openfuture.snapshot.api

import java.math.BigDecimal

data class Transfer(
        val fromAddress: String,
        val toAddress: String,
        val amount: BigDecimal
)
