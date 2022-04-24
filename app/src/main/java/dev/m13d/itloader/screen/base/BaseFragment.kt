package dev.m13d.itloader.screen.base

import androidx.fragment.app.Fragment

abstract class BaseFragment(fragment: Int) : Fragment(fragment) {

    abstract val viewModel: BaseViewModel

}
