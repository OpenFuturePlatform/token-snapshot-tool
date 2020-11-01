package io.openfuture.snapshot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan("io.openfuture.snapshot.property")
@SpringBootApplication
class ToolApplication

fun main(args: Array<String>) {
    runApplication<ToolApplication>(*args)
}
