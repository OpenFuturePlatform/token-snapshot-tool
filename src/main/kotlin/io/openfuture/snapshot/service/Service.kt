package io.openfuture.snapshot.service

import io.openfuture.snapshot.model.Snapshot

/**
 * @author Igor Pahomov
 */
interface SnapshotService {

    fun batchSnapshotToCsvFile(snapshot: Snapshot, fileName: String)

}

interface CommandLine {

    fun start(args: Array<out String?>)

}