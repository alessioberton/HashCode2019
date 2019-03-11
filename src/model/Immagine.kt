package model

class Immagine {
    var id: List<Int>? = null
    var verticale: Boolean? = null
    var numeroTag: Int? = null
    var listaTag: ArrayList<String>? = null

    internal constructor() {}

    constructor(id: List<Int>?, verticale: Boolean?, numeroTag: Int?, listaTag: ArrayList<String>?) {
        this.id = id
        this.verticale = verticale
        this.numeroTag = numeroTag
        this.listaTag = listaTag
    }

    override fun toString(): String {
        return "Immagine(id=$id, verticale=$verticale, numeroTag=$numeroTag, listaTag=$listaTag)"
    }

}