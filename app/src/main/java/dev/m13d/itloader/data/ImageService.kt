package dev.m13d.itloader.data

import android.os.Environment
import dev.m13d.itloader.MainActivity
import java.io.File

typealias ImageListener = (images: List<ItImage>) -> Unit

class ImageService {

    private var images = mutableListOf<ItImage>()
    private val listeners = mutableListOf<ImageListener>()

    init {
        val fileName =
            (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + MainActivity.FILE_NAME)).path
        File(fileName).readLines().forEachIndexed { index, s ->
            images += ItImage(url = s, id = index)
        }
    }

    fun getImages() = images

    private fun notifyChanges() {
        listeners.forEach { it.invoke(images) }
    }

    private fun findIndexById(imageId: Int): Int = images.indexOfFirst { it.id == imageId }

    fun addListener(listener: ImageListener) {
        listeners.add(listener)
        listener.invoke(images)
    }

    fun removeListener(listener: ImageListener) {
        listeners.remove(listener)
    }
}
