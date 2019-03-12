package logic

import model.Immagine
import model.Slide
import model.SlideShow
import util.Utils.Companion.accorpaIdImmaginiVerticali
import util.Utils.Companion.accorpaTagImmaginiVerticali
import util.Utils.Companion.findDupes
import util.Utils.Companion.trovaStessiElementi

class LogicaAccorpamento {

    //TODO A: Per arrivare a fare qualche punto in più, è necessario fare un ulteriore step. Riesci a trovarlo ??

    fun creaListaImmaginiVerticale(imageList: MutableList<Immagine>): MutableList<Immagine> {
        val verticalArray: MutableList<Immagine> = mutableListOf()
        imageList.forEach {
            if (it.verticale == true) {
                verticalArray.add(it)
            }
        }
        verticalArray.sortBy { it.numeroTag }
        return verticalArray.toMutableList()
        // return verticalArray.reversed().toMutableList()
    }

    fun miglioreCoppiaPossibile(imageVerticalList: MutableList<Immagine>): MutableList<Immagine> {
        val newImageListFromVertical: MutableList<Immagine> = mutableListOf()
        while (imageVerticalList.size > 0) {
            var k = imageVerticalList.size - 1
            while (k > 1 && imageVerticalList.size > 1 && trovaStessiElementi(imageVerticalList[0].listaTag!!, imageVerticalList[k].listaTag!!) > 0) {
                k--
            }

            val imageTmp = Immagine()
            imageTmp.id = accorpaIdImmaginiVerticali(imageVerticalList[0].id!!, imageVerticalList[k].id!!)
            imageTmp.verticale = false
            imageTmp.listaTag =
                accorpaTagImmaginiVerticali(imageVerticalList[0].listaTag!!, imageVerticalList[k].listaTag!!)
            imageTmp.numeroTag = imageTmp.listaTag!!.size
            imageVerticalList.remove(imageVerticalList[k])
            imageVerticalList.remove(imageVerticalList[0])
            newImageListFromVertical.add(imageTmp)
        }

        newImageListFromVertical.sortBy { it.numeroTag }
        return newImageListFromVertical
    }

    fun slideMigliorePossibile(imageVerticalList: MutableList<Immagine>, indextToNotCheck: Int): Slide {
        val singleSlideShow = SlideShow()
        var immagineDaComparare = Immagine()
        var maxValue = 0
        val listaPossibileNewSlide: MutableList<Triple<Int, Int, Int>> = mutableListOf()
        immagineDaComparare = imageVerticalList[indextToNotCheck]
        var listaTag = immagineDaComparare.numeroTag!!
        imageVerticalList.remove(immagineDaComparare)
        var indiceDaUsare = 0

        imageVerticalList.forEach {
            listaPossibileNewSlide.add(findDupes(immagineDaComparare.listaTag!!, it.listaTag!!))
        }

        listaPossibileNewSlide.sortBy { it.first }

        listaPossibileNewSlide.forEachIndexed { index, triple ->
            val minValue = triple.toList().min()!!
            val listaTagValue = triple.third
            if (minValue > maxValue) {
                listaTag = listaTagValue
                maxValue = minValue
                indiceDaUsare = index
            }

        }
        singleSlideShow.coppiaImmagini = Pair(immagineDaComparare, imageVerticalList[indiceDaUsare])
        singleSlideShow.puntiInteresse = maxValue
        return Slide(singleSlideShow, indiceDaUsare)
    }

    fun generaCoppia(arrayImmaginiOrdinato: MutableList<Immagine>, sizeArray: Int): MutableList<Immagine> {
        val listSlideShow: ArrayList<SlideShow> = ArrayList()
        var indiceDaNonUsare = 0
        val arrayCloneOrdinato: MutableList<Immagine> = ArrayList()
        arrayCloneOrdinato.addAll(arrayImmaginiOrdinato)

        for (i in 0..arrayImmaginiOrdinato.size) {
            var singleSlideAppoggio = Slide()
            var singleSlide = SlideShow()
            if (i == 0) {
                singleSlideAppoggio = slideMigliorePossibile(arrayCloneOrdinato, i)
                singleSlide = singleSlideAppoggio.slide!!
                indiceDaNonUsare = singleSlideAppoggio.indice!!
                listSlideShow.add(singleSlide)
            } else {
                if (arrayCloneOrdinato.size > 1) {
                    singleSlideAppoggio = slideMigliorePossibile(arrayCloneOrdinato, indiceDaNonUsare)
                    singleSlide = singleSlideAppoggio.slide!!
                    indiceDaNonUsare = singleSlideAppoggio.indice!!
                    listSlideShow.add(singleSlide)
                }
            }

            // Scommenta per un debug migliore
            if (singleSlide.coppiaImmagini != null) {
                //     println("Coppia numero: " + i + "    " + singleSlide.coppiaImmagini + "  +  punti interesse:  " + singleSlide.puntiInteresse)
            }
        }

        arrayImmaginiOrdinato.clear()

        for (i in 0 until sizeArray - 2) {
            if (listSlideShow[i].coppiaImmagini != null) {
                if (i == 0) {
                    arrayImmaginiOrdinato.add(listSlideShow[i].coppiaImmagini?.first!!)
                    arrayImmaginiOrdinato.add(listSlideShow[i].coppiaImmagini?.second!!)
                } else {
                    arrayImmaginiOrdinato.add(listSlideShow[i].coppiaImmagini?.second!!)
                }
            }
        }

        var puntiInteresseTot = 0
        listSlideShow.forEach {
            if (it.puntiInteresse != null) {
                puntiInteresseTot += it.puntiInteresse!!
            }
        }
        println("Punti interesse totali: $puntiInteresseTot")
        return arrayImmaginiOrdinato
    }
}
