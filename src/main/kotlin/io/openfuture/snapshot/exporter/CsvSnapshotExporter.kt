package io.openfuture.snapshot.exporter

import java.io.PrintWriter
import java.math.BigDecimal

class CsvSnapshotExporter {

    fun writeResult(outputName: String, balances: Map<String, BigDecimal>) {
        val writer = PrintWriter(outputName, "UTF-8")
        writer.println(HEADERS)
        balances.forEach { writer.println("${it.key},${it.value}") }
        writer.flush()
        writer.close()
    }

    companion object {
        const val HEADERS = "ADDRESS,BALANCE"
    }

}
