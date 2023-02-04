package no.nav.familie.ef.vedtakhendelse.vedtak

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(private val kafkaTemplate: KafkaTemplate<String, String>) {

    @Value("\${VEDTAK_TOPIC}")
    lateinit var topic: String

    fun send(payload: String) {
        kafkaTemplate.send(topic, payload).get()
    }
}
