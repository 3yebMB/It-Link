package dev.m13d.itloader.screen.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.m13d.itloader.Event
import dev.m13d.itloader.navigator.Navigator
import dev.m13d.itloader.screen.base.BaseViewModel
import dev.m13d.itloader.screen.gallery.GalleryFragment.*
import dev.m13d.itloader.screen.image.ImageFragment

class GalleryViewModel(
    private val navigator: Navigator,
    screen: Screen
) : BaseViewModel() {

    private val _currentMessageLiveData = MutableLiveData<List<String>>()
    val currentMessageLiveData: LiveData<List<String> = _currentMessageLiveData

    val _initialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEvent: LiveData<Event<String>> = _initialMessageEvent

    init {
//        _currentMessageLiveData.value = navigator.getString(R.string.hello_world)
    }

    init {
        _initialMessageEvent.value = Event(screen.initialValue)
    }

    override fun onResult(result: Any) {
        if (result is String) {
            _currentMessageLiveData.value = result
        }
    }


//    fun onItemPressed() {
//        navigator.launch(ImageFragment.Screen(initialValue = currentMessageLiveData.value!!))
//    }
}
