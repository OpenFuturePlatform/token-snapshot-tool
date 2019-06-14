package io.snapshot.tool.service

import io.snapshot.tool.model.Snapshot

/**
 * @author Igor Pahomov
 */
interface SnapshotService {

    fun batchSnapshotToCsvFile(snapshot: Snapshot, fileName: String)

}

interface CommandLine {

    fun start(args: Array<out String?>)

}