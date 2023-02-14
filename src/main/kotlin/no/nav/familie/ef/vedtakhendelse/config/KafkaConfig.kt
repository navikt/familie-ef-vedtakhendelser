package no.nav.familie.ef.vedtakhendelse.config

import no.nav.familie.kafka.KafkaErrorHandler
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties

@EnableKafka
@Configuration
class KafkaConfig {

    @Bean
    fun sykepengerListenerContainerFactory(properties: KafkaProperties, kafkaErrorHandler: KafkaErrorHandler):
        ConcurrentKafkaListenerContainerFactory<String, String> {

        val consumerConfig = mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to "familie-ef-sykepengevedtak",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to "1"
        )

        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.consumerFactory = DefaultKafkaConsumerFactory(consumerConfig)
        factory.setCommonErrorHandler(kafkaErrorHandler)
        return factory
    }
}
