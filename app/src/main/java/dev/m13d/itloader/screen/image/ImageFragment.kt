package dev.m13d.itloader.screen.image

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.m13d.itloader.R
import dev.m13d.itloader.databinding.FragmentImageBinding
import dev.m13d.itloader.screen.base.BaseFragment
import dev.m13d.itloader.screen.base.BaseScreen
import dev.m13d.itloader.screen.base.screenViewModel

//private const val ARG_URL = "url"

class ImageFragment : BaseFragment(R.layout.fragment_image) {

    class Screen(
        val initialValue: String
    ) : BaseScreen

    override val viewModel by screenViewModel<ImageViewModel>()
    private val binding by viewBinding(FragmentImageBinding::bind)

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }

        Glide.with(this@ImageFragment)
            .load(url)
            .error(R.drawable.ic_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.isVisible = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.isVisible = false
                    return false
                }
            })
            .into(binding.imageView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val url = arguments?.getString(ARG_URL)
            viewModel.initialMessageEvent.observe(viewLifecycleOwner) {
                it.getValue()?.let {
                    /*message -> binding.valueEditText.setText(message)*/
                    Glide.with(this@ImageFragment)
                        .load(url)
                        .error(R.drawable.ic_error)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false
                                return false
                            }
                        })
                        .into(imageView)
                }
            }
        }

        binding.apply {
            val image = arguments?.getString(ARG_URL)

            Glide.with(this@ImageFragment)
                .load(viewModel.initialMessageEvent)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }
                })
                .into(imageView)
        }
//        binding.saveButton.setOnClickListener {
//            viewModel.onSavePressed(binding.valueEditText.text.toString())
//        }
//        binding.cancelButton.setOnClickListener {
//            viewModel.onCancelPressed()
//        }
        binding.imageView.setOnClickListener {
            viewModel.onBackPressed()
        }
    }

    companion object {

        const val ARG_URL = "url"
        const val FRAGMENT_IMAGE_TAG = "FRAGMENT_IMAGE_TAG"

        @JvmStatic
        fun newInstance(url: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }

}
