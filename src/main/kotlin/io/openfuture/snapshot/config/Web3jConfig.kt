package io.openfuture.snapshot.config

import io.openfuture.snapshot.property.Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class Web3jConfig {

    @Bean
    fun web3j(properties: Properties): Web3j = Web3j.build(HttpService(properties.nodeAddress))

}
