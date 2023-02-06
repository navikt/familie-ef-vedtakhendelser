package no.nav.familie.ef.vedtakhendelse.sykepenger

data class Sykepengevedtak(
    val fødselsnummer: String,
    val fom: String,
    val tom: String,
    val skjæringstidspunkt: String,
    val sykepengegrunnlag: Int,
    val grunnlagForSykepengegrunnlag: Int,
)
