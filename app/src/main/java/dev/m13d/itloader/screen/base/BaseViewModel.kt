package dev.m13d.itloader.screen.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    open fun onResult(result: Any) {}

}
