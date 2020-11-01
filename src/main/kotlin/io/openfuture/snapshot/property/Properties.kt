package io.openfuture.snapshot.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ConstructorBinding
@ConfigurationProperties
@Validated
data class Properties(
        @field:NotNull val from: Int?,
        @field:NotNull val to: Int?,
        @field:NotNull @field:NotBlank val nodeAddress: String?,
        @field:NotNull @field:NotBlank val fileName: String?,
        @field:NotNull @field:NotBlank val contractAddress: String?
)
