package no.nav.familie.ef.vedtakhendelse.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import no.nav.familie.kafka.KafkaErrorHandler
import no.nav.familie.kontrakter.felles.objectMapper
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootConfiguration
@ConfigurationPropertiesScan("no.nav.familie.ef.vedtakhendelse")
@EnableScheduling
@Import(
    KafkaErrorHandler::class,
)
class ApplicationConfig {

    @Bean
    fun kotlinModule(): KotlinModule = KotlinModule.Builder().build()

    @Bean
    @Primary
    fun objectMapper() = objectMapper
}
