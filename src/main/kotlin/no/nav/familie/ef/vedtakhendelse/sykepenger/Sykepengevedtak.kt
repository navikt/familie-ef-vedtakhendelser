package no.nav.familie.ef.vedtakhendelse.sykepenger

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class Sykepengevedtak(
    val fødselsnummer: String,
    val fom: String,
    val tom: String,
    val skjæringstidspunkt: String,
    val sykepengegrunnlag: Int,
    val grunnlagForSykepengegrunnlag: Int,
)

data class SykepengevedtakInfotrygd(
    @JsonProperty("UTBET_TOM")
    @JsonAlias("utbet_TOM")
    val utbetalingTom: String?,
    @JsonProperty("MAX_DATO")
    @JsonAlias("max_DATO")
    val maxDato: String?,
    @JsonProperty("F_NR")
    @JsonAlias("f_NR")
    val fødselsnummer: String,
)
