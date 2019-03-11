package model

class Slide {

    var slide: SlideShow? = null
    var indice: Int ?= null

    internal constructor() {}

    constructor(slide: SlideShow?, indice: Int?) {
        this.slide = slide
        this.indice = indice
    }

    override fun toString(): String {
        return "Slide(slide=$slide, indice=$indice)"
    }


}