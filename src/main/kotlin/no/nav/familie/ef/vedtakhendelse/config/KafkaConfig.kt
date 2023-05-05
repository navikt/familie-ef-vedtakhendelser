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
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.LoggingProducerListener

@EnableKafka
@Configuration
class KafkaConfig {

    @Bean
    fun kafkaListenerContainerFactory(properties: KafkaProperties, kafkaErrorHandler: KafkaErrorHandler):
        ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = properties.buildConsumerProperties()
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(props)
        factory.setCommonErrorHandler(kafkaErrorHandler)
        return factory
    }

    @Bean
    fun kafkaTemplate(properties: KafkaProperties): KafkaTemplate<String, String> {
        val producerListener = LoggingProducerListener<String, String>()
        producerListener.setIncludeContents(false)
        val producerFactory = DefaultKafkaProducerFactory<String, String>(properties.buildProducerProperties())
        return KafkaTemplate(producerFactory).apply<KafkaTemplate<String, String>> {
            setProducerListener(producerListener)
        }
    }
}
