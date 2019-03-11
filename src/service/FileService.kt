package service

import model.Immagine
import java.nio.file.Files
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


class FileService {
    companion object {

        fun readBufferFile(path: String): MutableList<String> {
            return Files.readAllLines(java.nio.file.Paths.get(path), StandardCharsets.UTF_8)
        }

        fun writeBufferFile(path: String, listSlide: MutableList<Immagine>) {
            val writer = PrintWriter(path, "UTF-8")
            writer.println(listSlide.size)
            listSlide.forEach {
                var str = ""
                for (i in 0 until it.id!!.size) {
                    str += Integer.toString(it.id!![i])
                    str += " "
                }
                writer.println(str)
            }
            writer.close()
        }
    }
}