package io.snapshot.tool.service;

import java.lang.System;

/**
 * * @author Igor Pahomov
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lio/snapshot/tool/service/SnapshotService;", "", "batchSnapshotToCsvFile", "", "snapshot", "Lio/snapshot/tool/model/Snapshot;", "fileName", "", "tool"})
public abstract interface SnapshotService {
    
    public abstract void batchSnapshotToCsvFile(@org.jetbrains.annotations.NotNull()
    io.snapshot.tool.model.Snapshot snapshot, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName);
}