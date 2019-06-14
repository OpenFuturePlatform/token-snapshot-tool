package io.snapshot.tool.component;

import java.lang.System;

/**
 * * @author Igor Pahomov
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0017\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0012J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0012J\"\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00112\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00140\u0013H\u0012J(\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\"\u0010\u0017\u001a\u00020\u00182\u0006\u0010\t\u001a\u00020\u00062\b\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0019\u001a\u00020\u000bH\u0016J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0092\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lio/snapshot/tool/component/Web3jWrapper;", "", "web3j", "Lorg/web3j/protocol/Web3j;", "(Lorg/web3j/protocol/Web3j;)V", "server", "", "createTransferFilter", "Lorg/web3j/protocol/core/methods/request/EthFilter;", "address", "fromBlock", "", "toBlock", "decodeAddress", "Lorg/web3j/abi/datatypes/Address;", "rawData", "fetchAddressesFromLogs", "", "transferLogs", "", "Lorg/web3j/protocol/core/methods/response/EthLog$LogResult;", "getAddressesFromTransferEvents", "tokenAddress", "getTokenBalanceAtBlock", "Ljava/math/BigInteger;", "blockNumber", "init", "", "Companion", "tool"})
@org.springframework.stereotype.Component()
public class Web3jWrapper {
    private java.lang.String server;
    private org.web3j.protocol.Web3j web3j;
    private static final java.lang.String TRANSFER_EVENT = "Transfer";
    private static final java.lang.String BALANCE_METHOD = "balanceOf";
    private static final java.lang.String ERROR_TRANSACTION_STATUS = "0x0";
    private static final int POOL_SIZE = 10;
    public static final io.snapshot.tool.component.Web3jWrapper.Companion Companion = null;
    
    public void init(@org.jetbrains.annotations.Nullable()
    java.lang.String server) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.util.Set<org.web3j.abi.datatypes.Address> getAddressesFromTransferEvents(@org.jetbrains.annotations.Nullable()
    java.lang.String tokenAddress, int fromBlock, int toBlock) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.math.BigInteger getTokenBalanceAtBlock(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.String tokenAddress, int blockNumber) {
        return null;
    }
    
    private org.web3j.protocol.core.methods.request.EthFilter createTransferFilter(java.lang.String address, int fromBlock, int toBlock) {
        return null;
    }
    
    private java.util.Set<org.web3j.abi.datatypes.Address> fetchAddressesFromLogs(java.util.List<? extends org.web3j.protocol.core.methods.response.EthLog.LogResult<java.lang.Object>> transferLogs) {
        return null;
    }
    
    private org.web3j.abi.datatypes.Address decodeAddress(java.lang.String rawData) {
        return null;
    }
    
    public Web3jWrapper(@org.jetbrains.annotations.NotNull()
    org.web3j.protocol.Web3j web3j) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lio/snapshot/tool/component/Web3jWrapper$Companion;", "", "()V", "BALANCE_METHOD", "", "ERROR_TRANSACTION_STATUS", "POOL_SIZE", "", "TRANSFER_EVENT", "tool"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}