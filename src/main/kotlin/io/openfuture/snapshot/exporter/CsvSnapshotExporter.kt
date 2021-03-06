package io.openfuture.snapshot.exporter

import io.openfuture.snapshot.domain.WalletState
import java.io.PrintWriter

class CsvSnapshotExporter : FileExporter {

    override fun export(fileName: String, walletStates: Collection<WalletState>) {
        val writer = PrintWriter(fileName, "UTF-8")
        writer.println(HEADERS)
        walletStates.forEach {
            writer.println("${it.address},${it.balance}")
        }
        writer.close()
    }

    companion object {
        const val HEADERS = "ADDRESS,BALANCE"
    }

}
