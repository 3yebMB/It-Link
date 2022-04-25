package dev.m13d.itloader.navigator

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import dev.m13d.itloader.screen.base.BaseScreen

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)

    fun toast(@StringRes messageRes: Int)

    fun getString(@StringRes messageRes: Int): String

}
