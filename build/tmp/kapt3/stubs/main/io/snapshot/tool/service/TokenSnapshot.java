package io.snapshot.tool.service;

import java.lang.System;

/**
 * * @author Igor Pahomov
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 ,2\u00020\u0001:\u0001,BK\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0003H\u0002J\"\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u001f0\u001eH\u0002J\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190\u001c2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\"\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0\"2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00190\u001cH\u0002J\u0006\u0010%\u001a\u00020&J\u0018\u0010\'\u001a\u00020&2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u0002\u001a\u00020\u0019H\u0002J\u001c\u0010*\u001a\u00020&2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0\"H\u0002R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000fR\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000fR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lio/snapshot/tool/service/TokenSnapshot;", "", "address", "", "fromBlock", "", "toBlock", "decimals", "batchSize", "server", "filename", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;IILjava/lang/String;Ljava/lang/String;)V", "executor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "Ljava/lang/Integer;", "syncAddresses", "", "web3j", "Lorg/web3j/protocol/Web3j;", "writer", "Ljava/io/PrintWriter;", "createTransferFilter", "Lorg/web3j/protocol/core/methods/request/EthFilter;", "decodeAddress", "Lorg/web3j/abi/datatypes/Address;", "rawData", "fetchAddressesFromLogs", "", "transferLogs", "", "Lorg/web3j/protocol/core/methods/response/EthLog$LogResult;", "getAddressesFromTransferEvents", "getBalancesAtBlock", "", "Ljava/math/BigDecimal;", "addresses", "getBatchSnapshotToCsv", "", "getSnapshotData", "getTokenBalance", "Ljava/math/BigInteger;", "writeResult", "results", "Companion", "tool"})
public final class TokenSnapshot {
    private java.io.PrintWriter writer;
    private org.web3j.protocol.Web3j web3j;
    private java.util.concurrent.ExecutorService executor;
    private final java.util.Set<java.lang.String> syncAddresses = null;
    private final java.lang.String address = null;
    private final java.lang.Integer fromBlock = null;
    private final java.lang.Integer toBlock = null;
    private final int decimals = 0;
    private final int batchSize = 0;
    private final java.lang.String server = null;
    private final java.lang.String filename = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HEADER = "ADDRESS,BALANCE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TRANSFER_EVENT = "Transfer";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BALANCE_METHOD = "balanceOf";
    public static final io.snapshot.tool.service.TokenSnapshot.Companion Companion = null;
    
    public final void getBatchSnapshotToCsv() {
    }
    
    private final void getSnapshotData(int fromBlock, int toBlock) {
    }
    
    private final java.util.Set<org.web3j.abi.datatypes.Address> getAddressesFromTransferEvents(int fromBlock, int toBlock) {
        return null;
    }
    
    private final org.web3j.protocol.core.methods.request.EthFilter createTransferFilter(int fromBlock, int toBlock) {
        return null;
    }
    
    private final java.util.Set<org.web3j.abi.datatypes.Address> fetchAddressesFromLogs(java.util.List<? extends org.web3j.protocol.core.methods.response.EthLog.LogResult<java.lang.Object>> transferLogs) {
        return null;
    }
    
    private final java.util.Map<java.lang.String, java.math.BigDecimal> getBalancesAtBlock(java.util.Set<? extends org.web3j.abi.datatypes.Address> addresses) {
        return null;
    }
    
    private final java.math.BigInteger getTokenBalance(org.web3j.abi.datatypes.Address address) {
        return null;
    }
    
    private final org.web3j.abi.datatypes.Address decodeAddress(java.lang.String rawData) {
        return null;
    }
    
    private final void writeResult(java.util.Map<java.lang.String, ? extends java.math.BigDecimal> results) {
    }
    
    public TokenSnapshot(@org.jetbrains.annotations.Nullable()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.Integer fromBlock, @org.jetbrains.annotations.Nullable()
    java.lang.Integer toBlock, int decimals, int batchSize, @org.jetbrains.annotations.NotNull()
    java.lang.String server, @org.jetbrains.annotations.NotNull()
    java.lang.String filename) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lio/snapshot/tool/service/TokenSnapshot$Companion;", "", "()V", "BALANCE_METHOD", "", "HEADER", "TRANSFER_EVENT", "tool"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}