package no.nav.familie.ef.vedtakhendelse.foreldrepenger

import no.nav.abakus.vedtak.ytelse.v1.YtelseV1
import no.nav.familie.log.mdc.MDCConstants
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import java.util.*

class ForeldrepengevedtakListener(val foreldrepengevedtakService: ForeldrepengevedtakService) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val securelogger = LoggerFactory.getLogger("secureLogger")

    @KafkaListener(
        id = "familie-ef-foreldrepengevedtak-listener",
        groupId = "familie-ef-foreldrepengevedtak",
        topics = ["teamforeldrepenger.familie-vedtakfattet-v1"],
        autoStartup = "false",
    )
    fun listen(@Payload foreldrepengevedtak: YtelseV1) {
        try {
            MDC.put(MDCConstants.MDC_CALL_ID, UUID.randomUUID().toString())
            foreldrepengevedtakService.handleForeldrepengevedtak(foreldrepengevedtak)
            logger.info("Leser sykepengevedtak")
        } catch (e: Exception) {
            logger.error("Feil ved håndtering av sykepengehendelse")
            throw e
        } finally {
            MDC.remove(MDCConstants.MDC_CALL_ID)
        }
    }
}
