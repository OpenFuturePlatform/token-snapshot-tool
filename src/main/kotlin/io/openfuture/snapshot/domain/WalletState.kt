package io.openfuture.snapshot.domain

import java.math.BigDecimal

data class WalletState(
        val address: String,
        val balance: BigDecimal
)
