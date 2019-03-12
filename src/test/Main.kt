package test

import logic.LogicaAccorpamento
import model.Immagine
import service.FileService
import util.Utils.Companion.creaListaImmagini
import util.Utils.Companion.mergeHorizontalWithNewHorizontal
import kotlin.concurrent.thread

fun main() {
    val logicaAccorpamento = LogicaAccorpamento()

    val pathTwo = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\b_lovely_landscapes.txt"
    val pathThree = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\c_memorable_moments.txt"
    val pathFour = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\d_pet_pictures.txt"
    val pathFive = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\e_shiny_selfies.txt"
    val listaFileInput = arrayOf(pathTwo, pathThree, pathFour, pathFive)

    val pathOutputTwo = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\example2.txt"
    val pathOutputThree = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\example3.txt"
    val pathOutputFour = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\example4.txt"
    val pathOutputFive = "C:\\Users\\alessio.berton\\Downloads\\HashCode2019\\src\\example5.txt"
    val listaFileOutput = arrayOf(pathOutputTwo, pathOutputThree, pathOutputFour, pathOutputFive)

    val listaImmaginiRaw = arrayListOf<MutableList<String>>()
    listaFileInput.forEach {
        listaImmaginiRaw += FileService.readBufferFile(it)
    }

    val listaImmaginiVerticaliOrrizontali = arrayListOf<MutableList<Immagine>>()
    listaImmaginiRaw.forEach {
        listaImmaginiVerticaliOrrizontali += creaListaImmagini(it)
    }

    val listaImmaginiSoloOrrizontali = arrayListOf<MutableList<Immagine>>()
    listaImmaginiVerticaliOrrizontali.forEach {
        listaImmaginiSoloOrrizontali += logicaAccorpamento.creaListaImmaginiVerticale(it)
    }

    val listaMiglioreCoppiaVerticalePossibile = arrayListOf<MutableList<Immagine>>()
    listaImmaginiSoloOrrizontali.forEachIndexed { index, mutableList ->
        listaMiglioreCoppiaVerticalePossibile += logicaAccorpamento.miglioreCoppiaPossibile(mutableList)
    }

    val listaOrdinataConCoppiaDaVerticale = arrayListOf<MutableList<Immagine>>()
    for (i in 0 until listaMiglioreCoppiaVerticalePossibile.size) {
        listaOrdinataConCoppiaDaVerticale += mergeHorizontalWithNewHorizontal(
            listaImmaginiVerticaliOrrizontali[i],
            listaMiglioreCoppiaVerticalePossibile[i]
        )
    }

    listaOrdinataConCoppiaDaVerticale.forEachIndexed { index, mutableList ->
        thread {
            FileService.writeBufferFile(listaFileOutput[index], logicaAccorpamento.generaCoppia(mutableList, mutableList.size))
        }
    }
}