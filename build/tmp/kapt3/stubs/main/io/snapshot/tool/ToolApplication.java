package io.snapshot.tool;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u00020\u00062\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\t0\b\"\u0004\u0018\u00010\tH\u0016\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lio/snapshot/tool/ToolApplication;", "Lorg/springframework/boot/CommandLineRunner;", "commandLine", "Lio/snapshot/tool/service/CommandLine;", "(Lio/snapshot/tool/service/CommandLine;)V", "run", "", "args", "", "", "([Ljava/lang/String;)V", "tool"})
@org.springframework.boot.autoconfigure.SpringBootApplication()
public class ToolApplication implements org.springframework.boot.CommandLineRunner {
    private final io.snapshot.tool.service.CommandLine commandLine = null;
    
    @java.lang.Override()
    public void run(@org.jetbrains.annotations.NotNull()
    java.lang.String... args) {
    }
    
    public ToolApplication(@org.jetbrains.annotations.NotNull()
    io.snapshot.tool.service.CommandLine commandLine) {
        super();
    }
}