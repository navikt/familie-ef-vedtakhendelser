package main.kotlin.no.nav.familie.ef.vedtakhendelse.sykepenger

import no.nav.familie.ef.vedtakhendelse.sykepenger.SykepengevedtakInfotrygd
import no.nav.familie.ef.vedtakhendelse.sykepenger.SykepengevedtakService
import no.nav.familie.log.mdc.MDCConstants
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class SykepengevedtakInfotrygdListener(
    private val sykepengevedtakService: SykepengevedtakService,
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val securelogger = LoggerFactory.getLogger("secureLogger")

    @KafkaListener(
        id = "familie-ef-sykepengevedtak-infotrygd-listener",
        groupId = "familie-ef-sykepengevedtak-infotrygd",
        topics = ["aap.sykepengedager.infotrygd.v1"],
        autoStartup = "false",
    )
    fun listen(@Payload sykepengevedtakInfotrygdHendelse: SykepengevedtakInfotrygd) {
        try {
            MDC.put(MDCConstants.MDC_CALL_ID, UUID.randomUUID().toString())
            logger.info("Leser sykepengevedtak fra infotrygd")
        } catch (e: Exception) {
            logger.error("Feil ved håndtering av sykepengehendelse fra infotrygd")
            throw e
        } finally {
            MDC.remove(MDCConstants.MDC_CALL_ID)
        }
    }
}
