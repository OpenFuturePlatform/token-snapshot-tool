package io.snapshot.tool.service;

import java.lang.System;

/**
 * * @author Igor Pahomov
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016JC\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016H\u0012\u00a2\u0006\u0002\u0010\u0018J$\u0010\u0019\u001a\u00020\t2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0012R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lio/snapshot/tool/service/DefaultSnapshotService;", "Lio/snapshot/tool/service/SnapshotService;", "web3j", "Lio/snapshot/tool/component/Web3jWrapper;", "(Lio/snapshot/tool/component/Web3jWrapper;)V", "executor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "batchSnapshotToCsvFile", "", "snapshot", "Lio/snapshot/tool/model/Snapshot;", "fileName", "", "getBalancesAtBlock", "", "Ljava/math/BigDecimal;", "addresses", "", "Lorg/web3j/abi/datatypes/Address;", "tokenAddress", "blockNumber", "", "decimals", "(Ljava/util/Set;Ljava/lang/String;ILjava/lang/Integer;)Ljava/util/Map;", "writeResult", "results", "writer", "Ljava/io/PrintWriter;", "Companion", "tool"})
@org.springframework.stereotype.Service()
public class DefaultSnapshotService implements io.snapshot.tool.service.SnapshotService {
    private final java.util.concurrent.ExecutorService executor = null;
    private final io.snapshot.tool.component.Web3jWrapper web3j = null;
    private static final int BATCH_SIZE = 10;
    private static final int POOL_SIZE = 10;
    private static final java.lang.String HEADER = "ADDRESS,BALANCE";
    public static final io.snapshot.tool.service.DefaultSnapshotService.Companion Companion = null;
    
    @java.lang.Override()
    public void batchSnapshotToCsvFile(@org.jetbrains.annotations.NotNull()
    io.snapshot.tool.model.Snapshot snapshot, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName) {
    }
    
    private java.util.Map<java.lang.String, java.math.BigDecimal> getBalancesAtBlock(java.util.Set<? extends org.web3j.abi.datatypes.Address> addresses, java.lang.String tokenAddress, int blockNumber, java.lang.Integer decimals) {
        return null;
    }
    
    private void writeResult(java.util.Map<java.lang.String, ? extends java.math.BigDecimal> results, java.io.PrintWriter writer) {
    }
    
    public DefaultSnapshotService(@org.jetbrains.annotations.NotNull()
    io.snapshot.tool.component.Web3jWrapper web3j) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lio/snapshot/tool/service/DefaultSnapshotService$Companion;", "", "()V", "BATCH_SIZE", "", "HEADER", "", "POOL_SIZE", "tool"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}