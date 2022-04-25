package dev.m13d.itloader.screen.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dev.m13d.itloader.R
import dev.m13d.itloader.data.ItImage
import dev.m13d.itloader.databinding.ImageItemBinding
import dev.m13d.itloader.screen.gallery.GalleryAdapter.ViewHolder

interface ImageActionListener {
    fun onImageClicked(image: ItImage)
}

class ImagesDiffCallback(
    private val oldList: List<ItImage>,
    private val newList: List<ItImage>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImage = oldList[oldItemPosition]
        val newImage = newList[newItemPosition]
        return oldImage == newImage
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImage = oldList[oldItemPosition]
        val newImage = newList[newItemPosition]
        return oldImage.id == newImage.id
    }
}

class GalleryAdapter(
    private val actionListener: ImageActionListener
) : RecyclerView.Adapter<ViewHolder>(), View.OnClickListener {

    var images = emptyList<ItImage>()
        set(newValue) {
            val diffCallback = ImagesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(
        val binding: ImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]

        with(holder.binding) {
            holder.itemView.tag = image

            if (image.url.isNotBlank()) {
                val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(iview.context)
                    .load(image.url)
                    .thumbnail(0.25f)
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_error)
                    .into(iview)
            } else {
                Glide.with(iview.context).clear(iview)
                iview.setImageResource(R.drawable.ic_logo)
            }
        }
    }

    override fun onClick(p0: View?) {
        val image = p0?.tag as ItImage
        actionListener.onImageClicked(image)
    }

}
