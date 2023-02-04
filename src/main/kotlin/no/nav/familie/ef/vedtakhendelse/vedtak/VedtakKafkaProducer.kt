package no.nav.familie.ef.vedtakhendelse.vedtak

import no.nav.familie.kontrakter.felles.objectMapper
import org.springframework.stereotype.Service

@Service
class VedtakKafkaProducer(private val kafkaProducerService: KafkaProducerService) {

    fun sendVedtak(hendelse: Vedtakhendelse) {
        kafkaProducerService.send(objectMapper.writeValueAsString(hendelse))
    }
}
