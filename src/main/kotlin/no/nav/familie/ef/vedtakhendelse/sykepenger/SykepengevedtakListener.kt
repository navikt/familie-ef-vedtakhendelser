package no.nav.familie.ef.vedtakhendelse.sykepenger

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.familie.kontrakter.felles.objectMapper
import no.nav.familie.log.mdc.MDCConstants
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.ConsumerSeekAware
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class SykepengevedtakListener(
    private val sykepengevedtakService: SykepengevedtakService,
) : ConsumerSeekAware {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val securelogger = LoggerFactory.getLogger("secureLogger")

    @KafkaListener(
        id = "familie-ef-sykepengevedtak-listener",
        groupId = "familie-ef-sykepengevedtak",
        topics = ["tbd.vedtak"],
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun listen(@Payload sykepengevedtakJson: String, @Headers headers: MessageHeaders) {
        try {
            MDC.put(MDCConstants.MDC_CALL_ID, UUID.randomUUID().toString())
            val sykepengevedtak = objectMapper.readValue<Sykepengevedtak>(sykepengevedtakJson)
            if (headers.any { it.key == "type" && it.value == "VedtakFattet" }) {
                sykepengevedtakService.handleSykepengevedtak(sykepengevedtak)
            }
            logger.info(
                "Leser sykepengevedtak med periode: ${sykepengevedtak.fom} -  ${sykepengevedtak.tom} " +
                    "Skjæringstidspunkt: ${sykepengevedtak.skjæringstidspunkt} " +
                    "Sykepengegrunnlag: ${sykepengevedtak.sykepengegrunnlag} " +
                    "GrunnlagForSykepengegrunnlag ${sykepengevedtak.grunnlagForSykepengegrunnlag}",
            )
        } catch (e: Exception) {
            logger.error("Feil ved håndtering av sykepengehendelse")
            throw e
        } finally {
            MDC.remove(MDCConstants.MDC_CALL_ID)
        }
    }

    /*
    override fun onPartitionsAssigned(
        assignments: MutableMap<org.apache.kafka.common.TopicPartition, Long>,
        callback: ConsumerSeekAware.ConsumerSeekCallback,
    ) {
        logger.info("overrided onPartitionsAssigned")
        assignments.keys.stream()
            .filter { it.topic() == "vedtak" }
            .forEach {
                callback.seekRelative("vedtak", it.partition(), -10L, false)
            }
    }
     */
}
