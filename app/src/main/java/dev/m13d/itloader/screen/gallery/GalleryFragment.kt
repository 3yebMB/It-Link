package dev.m13d.itloader.screen.gallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.m13d.itloader.App
import dev.m13d.itloader.R
import dev.m13d.itloader.data.ImageListener
import dev.m13d.itloader.data.ImageService
import dev.m13d.itloader.data.ItImage
import dev.m13d.itloader.databinding.FragmentGalleryBinding
import dev.m13d.itloader.screen.base.BaseFragment
import dev.m13d.itloader.screen.base.BaseScreen
import dev.m13d.itloader.screen.base.screenViewModel

private const val ARG_FILE_NAME = "file_name"

class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {

    class Screen(
        val initialValue: String
    ) : BaseScreen

    override val viewModel by screenViewModel<GalleryViewModel>()
    private val binding by viewBinding(FragmentGalleryBinding::bind)

    private lateinit var _adapter: GalleryAdapter
    private lateinit var layoutManager: LayoutManager

    private lateinit var cardViewClickListener: CardViewClickListener

    private val imageService: ImageService
        get() = (activity?.applicationContext as App).imageService

    private val contactsListener: ImageListener = {
        _adapter.images = it
    }

    private var fileName: String? = null

    interface CardViewClickListener {
        fun onCardViewClicked(image: ItImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fileName = it.getString(ARG_FILE_NAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvImages.setOnClickListener { viewModel.onItemPressed() }
        _adapter = GalleryAdapter(object : ImageActionListener {
            override fun onImageClicked(image: ItImage) {
                cardViewClickListener.onCardViewClicked(image)
            }
        })

        _adapter.images = imageService.getImages()

        binding.rvImages.adapter = _adapter

        val itemAnimator = binding.rvImages.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }

        imageService.addListener(contactsListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        imageService.removeListener(contactsListener)
    }

    companion object {

        // const val ARG_FILE_NAME = "file_name"
        const val GALLERY_FRAGMENT_TAG = "GALLERY_FRAGMENT_TAG"

        @JvmStatic
        fun newInstance(fileName: String) = GalleryFragment()
            .apply {
                arguments = Bundle().apply {
                    putString(ARG_FILE_NAME, fileName)
                }
            }
    }
}
