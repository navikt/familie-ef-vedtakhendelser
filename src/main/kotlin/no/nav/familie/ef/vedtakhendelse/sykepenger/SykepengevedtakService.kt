package no.nav.familie.ef.vedtakhendelse.sykepenger

import no.nav.familie.ef.vedtakhendelse.vedtak.VedtakKafkaProducer
import no.nav.familie.ef.vedtakhendelse.vedtak.Vedtakhendelse
import no.nav.familie.ef.vedtakhendelse.vedtak.Ytelse
import org.springframework.stereotype.Service

@Service
class SykepengevedtakService(val vedtakKafkaProducer: VedtakKafkaProducer) {
    fun handleSykepengevedtak(sykepengevedtakhendelse: Sykepengevedtak) {
        vedtakKafkaProducer.sendVedtak(Vedtakhendelse(sykepengevedtakhendelse.fødselsnummer, Ytelse.SYKEPENGER, sykepengevedtakhendelse.tom))
    }

    fun handleSykepengevedtakInfotrygd(sykepengevedtakInfotrygd: SykepengevedtakInfotrygd) {
        vedtakKafkaProducer.sendVedtak(Vedtakhendelse(sykepengevedtakInfotrygd.F_NR, Ytelse.SYKEPENGER, sykepengevedtakInfotrygd.MAX_DATO))
    }
}
