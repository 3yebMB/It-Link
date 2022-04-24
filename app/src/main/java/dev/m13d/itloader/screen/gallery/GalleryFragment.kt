package dev.m13d.itloader.screen.gallery

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.m13d.itloader.Event
import dev.m13d.itloader.R
import dev.m13d.itloader.databinding.FragmentGalleryBinding
import dev.m13d.itloader.screen.base.BaseFragment
import dev.m13d.itloader.screen.base.BaseScreen
import dev.m13d.itloader.screen.base.screenViewModel

class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {

    class Screen(
        val initialValue: String
    ) : BaseScreen

    override val viewModel by screenViewModel<GalleryViewModel>()
    private val binding by viewBinding(FragmentGalleryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.editButton.setOnClickListener { viewModel.onEditPressed() }

        viewModel.currentMessageLiveData.observe(viewLifecycleOwner) {
//            binding.valueTextView.text = it
            binding.rvImages.adapter = it
        }
    }

    companion object {

        const val GALLERY_FRAGMENT_TAG = "GALLERY_FRAGMENT_TAG"

        @JvmStatic
        fun newInstance(fileName: String) = GalleryFragment()
    }

}
