package no.nav.familie.ef.vedtakhendelse.vedtak

data class Vedtakhendelse(
    val fødselsnummer: String,
    val ytelse: Ytelse,
    val tilDato: String?,
)

enum class Ytelse {
    SYKEPENGER,
    FORELDREPENGER,
}
