package dev.m13d.itloader

import android.app.Application
import dev.m13d.itloader.data.ImageService

class App: Application() {

    val imageService = ImageService()
}
