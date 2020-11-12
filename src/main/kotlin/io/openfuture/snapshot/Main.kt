package io.openfuture.snapshot

import io.openfuture.snapshot.wrapper.Web3jWrapper
import io.openfuture.snapshot.exporter.SnapshotExporter
import io.openfuture.snapshot.runner.CommandLineSnapshotRunner

fun main(args: Array<String>) {
    val web3jWrapper = Web3jWrapper()
    val snapshotExporter = SnapshotExporter(web3jWrapper)
    val runner = CommandLineSnapshotRunner(snapshotExporter)
    runner.main(args)
}
