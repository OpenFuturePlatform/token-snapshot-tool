package io.snapshot.tool.service;

import java.lang.System;

/**
 * * @author Igor Pahomov
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010#\u001a\u00020$H\u0016J\u001f\u0010%\u001a\u00020$2\u0010\u0010&\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\t0\'H\u0016\u00a2\u0006\u0002\u0010(R\u001d\u0010\b\u001a\u0004\u0018\u00010\t8RX\u0092\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u000e\u001a\u00020\u000f8RX\u0092\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\t8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\r\u001a\u0004\b\u0014\u0010\u000bR\u001b\u0010\u0016\u001a\u00020\t8RX\u0092\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\r\u001a\u0004\b\u0017\u0010\u000bR\u001d\u0010\u0019\u001a\u0004\u0018\u00010\u000f8RX\u0092\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\r\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001d\u001a\u0004\u0018\u00010\t8RX\u0092\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001f\u0010\r\u001a\u0004\b\u001e\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010 \u001a\u0004\u0018\u00010\u000f8RX\u0092\u0084\u0002\u00a2\u0006\f\n\u0004\b\"\u0010\r\u001a\u0004\b!\u0010\u001bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lio/snapshot/tool/service/DefaultCommandLine;", "Lcom/github/ajalt/clikt/core/CliktCommand;", "Lio/snapshot/tool/service/CommandLine;", "snapshot", "Lio/snapshot/tool/service/SnapshotService;", "web3j", "Lio/snapshot/tool/component/Web3jWrapper;", "(Lio/snapshot/tool/service/SnapshotService;Lio/snapshot/tool/component/Web3jWrapper;)V", "address", "", "getAddress", "()Ljava/lang/String;", "address$delegate", "Lkotlin/properties/ReadOnlyProperty;", "decimals", "", "getDecimals", "()I", "decimals$delegate", "explic", "getExplic", "explic$delegate", "filename", "getFilename", "filename$delegate", "from", "getFrom", "()Ljava/lang/Integer;", "from$delegate", "server", "getServer", "server$delegate", "to", "getTo", "to$delegate", "run", "", "start", "args", "", "([Ljava/lang/String;)V", "tool"})
@org.springframework.stereotype.Component()
public class DefaultCommandLine extends com.github.ajalt.clikt.core.CliktCommand implements io.snapshot.tool.service.CommandLine {
    private final kotlin.properties.ReadOnlyProperty address$delegate = null;
    private final kotlin.properties.ReadOnlyProperty decimals$delegate = null;
    private final kotlin.properties.ReadOnlyProperty from$delegate = null;
    private final kotlin.properties.ReadOnlyProperty to$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private final kotlin.properties.ReadOnlyProperty explic$delegate = null;
    private final kotlin.properties.ReadOnlyProperty server$delegate = null;
    private final kotlin.properties.ReadOnlyProperty filename$delegate = null;
    private final io.snapshot.tool.service.SnapshotService snapshot = null;
    private final io.snapshot.tool.component.Web3jWrapper web3j = null;
    
    @java.lang.Override()
    public void start(@org.jetbrains.annotations.NotNull()
    java.lang.String[] args) {
    }
    
    private java.lang.String getAddress() {
        return null;
    }
    
    private int getDecimals() {
        return 0;
    }
    
    private java.lang.Integer getFrom() {
        return null;
    }
    
    private java.lang.Integer getTo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.String getExplic() {
        return null;
    }
    
    private java.lang.String getServer() {
        return null;
    }
    
    private java.lang.String getFilename() {
        return null;
    }
    
    @java.lang.Override()
    public void run() {
    }
    
    public DefaultCommandLine(@org.jetbrains.annotations.NotNull()
    io.snapshot.tool.service.SnapshotService snapshot, @org.jetbrains.annotations.NotNull()
    io.snapshot.tool.component.Web3jWrapper web3j) {
        super(null, null, null, false);
    }
}