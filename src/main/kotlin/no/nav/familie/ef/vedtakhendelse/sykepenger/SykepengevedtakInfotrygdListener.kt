package main.kotlin.no.nav.familie.ef.vedtakhendelse.sykepenger

import no.nav.familie.ef.vedtakhendelse.sykepenger.SykepengevedtakInfotrygd
import no.nav.familie.ef.vedtakhendelse.sykepenger.SykepengevedtakService
import no.nav.familie.log.mdc.MDCConstants
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class SykepengevedtakInfotrygdListener(
    private val sykepengevedtakService: SykepengevedtakService,
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val securelogger = LoggerFactory.getLogger("secureLogger")

    fun listen(@Payload sykepengevedtakInfotrygd: SykepengevedtakInfotrygd) {
        try {
            MDC.put(MDCConstants.MDC_CALL_ID, UUID.randomUUID().toString())
            sykepengevedtakService.handleSykepengevedtakInfotrygd(sykepengevedtakInfotrygd)
            logger.info("Leser sykepengevedtak fra infotrygd")
        } catch (e: Exception) {
            logger.error("Feil ved håndtering av sykepengehendelse fra infotrygd")
            throw e
        } finally {
            MDC.remove(MDCConstants.MDC_CALL_ID)
        }
    }
}
