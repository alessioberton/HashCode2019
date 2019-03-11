package model

class SlideShow {

    var coppiaImmagini: Pair<Immagine, Immagine>? = null
    var puntiInteresse: Int? = null

    internal constructor() {}

    constructor(coppiaImmagini: Pair<Immagine, Immagine>?, puntiInteresse: Int?) {
        this.coppiaImmagini = coppiaImmagini
        this.puntiInteresse = puntiInteresse
    }

    override fun toString(): String {
        return "SlideShow(coppiaImmagini=$coppiaImmagini, puntiInteresse=$puntiInteresse)"
    }

}