package no.nav.familie.ef.vedtakhendelse.foreldrepenger

import no.nav.abakus.vedtak.ytelse.v1.YtelseV1
import no.nav.familie.ef.vedtakhendelse.vedtak.VedtakKafkaProducer
import no.nav.familie.ef.vedtakhendelse.vedtak.Vedtakhendelse
import no.nav.familie.ef.vedtakhendelse.vedtak.Ytelse
import org.springframework.stereotype.Service

@Service
class ForeldrepengevedtakService(val vedtakKafkaProducer: VedtakKafkaProducer) {
    fun handleForeldrepengevedtak(foreldrepengevedtak: YtelseV1) {
        vedtakKafkaProducer.sendVedtak(Vedtakhendelse(foreldrepengevedtak.aktør.verdi, Ytelse.FORELDREPENGER, foreldrepengevedtak.periode.tom.toString()))
    }
}
// Relevante ytelser: PLEIEPENGER_SYKT_BARN, PLEIEPENGER_NÆRSTÅENDE, FORELDREPENGER
/* Ytelser
    PLEIEPENGER_SYKT_BARN,
    PLEIEPENGER_NÆRSTÅENDE,
    OMSORGSPENGER,
    OPPLÆRINGSPENGER,
    ENGANGSTØNAD, (irrelevant)
    FORELDREPENGER,
    SVANGERSKAPSPENGER,
    FRISINN;
 */
