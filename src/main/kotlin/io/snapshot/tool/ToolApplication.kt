package io.snapshot.tool

import io.snapshot.tool.service.CommandLine
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToolApplication(private val commandLine: CommandLine) : CommandLineRunner {

    override fun run(vararg args: String?) {
        commandLine.start(args)
    }
}

fun main(args: Array<String>) {
    runApplication<ToolApplication>(*args)
}



