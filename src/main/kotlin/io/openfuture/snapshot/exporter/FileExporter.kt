package io.openfuture.snapshot.exporter

import io.openfuture.snapshot.domain.WalletState

interface FileExporter {

    fun export(fileName: String, walletStates: Collection<WalletState>)

}
