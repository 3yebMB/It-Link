package dev.m13d.itloader.screen.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.m13d.itloader.Event
import dev.m13d.itloader.navigator.Navigator
import dev.m13d.itloader.screen.base.BaseViewModel
import dev.m13d.itloader.screen.image.ImageFragment.Screen

class ImageViewModel(
    private val navigator: Navigator,
    screen: Screen
) : BaseViewModel() {

    private val _initialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEvent: LiveData<Event<String>> = _initialMessageEvent

    init {
        _initialMessageEvent.value = Event(screen.initialValue)
    }

    fun onBackPressed() {
        navigator.goBack()
    }

}
