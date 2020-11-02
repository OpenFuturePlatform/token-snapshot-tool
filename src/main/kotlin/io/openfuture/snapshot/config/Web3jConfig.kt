package io.openfuture.snapshot.config

import io.openfuture.snapshot.property.ExportProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class Web3jConfig {

    @Bean
    fun web3j(exportProperties: ExportProperties): Web3j = Web3j.build(HttpService(exportProperties.nodeAddress))

}
