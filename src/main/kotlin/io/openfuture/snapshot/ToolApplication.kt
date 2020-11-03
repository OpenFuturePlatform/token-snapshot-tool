package io.openfuture.snapshot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToolApplication

fun main(args: Array<String>) {
    runApplication<ToolApplication>(*args)
}
