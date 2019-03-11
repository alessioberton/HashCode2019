package util

import model.Immagine
import java.util.*

class Utils {
    companion object {

        fun creaListaImmagini(imageLines: MutableList<String>): MutableList<Immagine> {
            val imageList: MutableList<Immagine> = mutableListOf()
            imageLines.forEachIndexed { index, s ->
                val imageTmp = Immagine()
                if (index != 0) {
                    val tokens = s.split(" ")
                    imageTmp.id = listOf(index - 1)
                    imageTmp.verticale = false
                    if (tokens[0] == "V") {
                        imageTmp.verticale = true
                    }
                    imageTmp.numeroTag = tokens[1].toInt()
                    val listOfTokens: ArrayList<String> = ArrayList()
                    for (i in 2 until tokens.size) {
                        listOfTokens.add(tokens[i])
                    }
                    imageTmp.listaTag = listOfTokens
                    imageList.add(imageTmp)
                }
            }
            return imageList
        }

        fun accorpaIdImmaginiVerticali(primaListaId: List<Int>, secondaListaId: List<Int>): ArrayList<Int> {
            val set = HashSet(primaListaId)
            set.addAll(secondaListaId)
            return ArrayList(set)
        }

        fun accorpaTagImmaginiVerticali(primaListaTag: List<String>, secondaListaTag: List<String>): ArrayList<String> {
            val set = HashSet(primaListaTag)
            set.addAll(secondaListaTag)
            return ArrayList(set)
        }

        fun mergeHorizontalWithNewHorizontal(arr1: MutableList<Immagine>, arr2: MutableList<Immagine>): MutableList<Immagine> {
            val verticalArray: MutableList<Immagine> = mutableListOf()
            arr1.forEachIndexed { index, immagine ->
                if (immagine.verticale == true) {
                    verticalArray.add(immagine)
                }
            }
            arr1.removeAll(verticalArray)
            arr1.addAll(arr2.toTypedArray())
            arr1.sortBy { it.numeroTag }
            return arr1
        }

        fun findDupes(a: ArrayList<String>, b: ArrayList<String>): Triple<Int, Int, Int> {
            val coppiaComuneNonComune: Triple<Int, Int, Int> = Triple(0, 0, 0)
            val map = HashSet<String>()
            var k = 0
            for (i in a)
                map.add(i);
            for (i in b) {
                if (map.contains(i))
                    k++
            }
            return coppiaComuneNonComune.copy(a.size - k, k, b.size - k)
        }

        fun trovaStessiElementi(a: ArrayList<String>, b: ArrayList<String>): Int {
            val map = HashSet<String>()
            var k = 0
            for (i in a)
                map.add(i);
            for (i in b) {
                if (map.contains(i))
                    k++
            }
            return k
        }

    }
}