package no.nav.familie.ef.vedtakhendelse.foreldrepenger

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.abakus.vedtak.ytelse.v1.YtelseV1
import no.nav.familie.kontrakter.felles.objectMapper
import no.nav.familie.log.mdc.MDCConstants
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class ForeldrepengevedtakListener(val foreldrepengevedtakService: ForeldrepengevedtakService) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val securelogger = LoggerFactory.getLogger("secureLogger")

    @KafkaListener(
        id = "familie-ef-foreldrepengevedtak-listener",
        groupId = "familie-ef-foreldrepengevedtak",
        topics = ["teamforeldrepenger.familie-vedtakfattet-v1"],
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun listen(@Payload foreldrepengevedtakJson: String) {
        try {
            MDC.put(MDCConstants.MDC_CALL_ID, UUID.randomUUID().toString())
            val foreldrepengevedtak = objectMapper.readValue<YtelseV1>(foreldrepengevedtakJson)
            // foreldrepengevedtakService.handleForeldrepengevedtak(foreldrepengevedtak)
            logger.info("Leser foreldrepengevedtak")
        } catch (e: Exception) {
            logger.error("Feil ved håndtering av foreldepengehendelse")
            throw e
        } finally {
            MDC.remove(MDCConstants.MDC_CALL_ID)
        }
    }
}
