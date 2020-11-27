package io.openfuture.snapshot.exporter

import io.openfuture.snapshot.domain.WalletState
import java.io.PrintWriter
import java.math.BigDecimal

class CsvSnapshotExporter : FileExporter {

    override fun export(fileName: String, walletStates: Set<WalletState>) {
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
