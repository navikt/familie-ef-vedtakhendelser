package no.nav.familie.ef.vedtakhendelse.vedtak

import java.time.LocalDate

data class Vedtakhendelse(
    val fødselsnummer: String,
    val ytelse: Ytelse,
    val fraDato: LocalDate?,
    val tilDato: LocalDate?,
)

enum class Ytelse {
    SYKEPENGER,
    FORELDREPENGER,
}
