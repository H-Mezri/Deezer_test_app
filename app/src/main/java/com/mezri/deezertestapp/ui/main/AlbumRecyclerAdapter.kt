package com.mezri.deezertestapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.mezri.deezertestapp.R
import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.model.ViewLoader
import com.mezri.deezertestapp.databinding.AlbumItemBinding
import com.mezri.deezertestapp.utils.glide.load
import kotlinx.android.synthetic.main.album_item.view.*

private const val VIEW_TYPE_ITEM = 0
private const val VIEW_TYPE_LOADING = 1

class AlbumRecyclerAdapter(
    val albumList: MutableList<Any>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(view: View, album: Album)
    }

    private val loaderView = ViewLoader()

    override fun getItemViewType(position: Int): Int {
        return when {
            albumList[position] is Album -> VIEW_TYPE_ITEM
            else -> VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val albumItemBinding = AlbumItemBinding.inflate(layoutInflater, parent, false)
                AlbumItemViewHolder(albumItemBinding)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.progress_loading, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {
        if (holderItem.itemViewType == VIEW_TYPE_ITEM) {
            (holderItem as AlbumItemViewHolder).bind(albumList[position] as Album)
        }
    }

    inner class AlbumItemViewHolder(private val albumItemBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(albumItemBinding.root) {
        fun bind(album: Album) {
            albumItemBinding.album = album

            // load album cover
            itemView.imgAlbumCover.load(album.cover_big) {}

            // init image view transition name
            ViewCompat.setTransitionName(itemView.imgAlbumCover, album.id.toString())

            // set view on click listener
            itemView.setOnClickListener {
                itemClickListener.onClick(itemView.imgAlbumCover, album)
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int = albumList.size

    /**
     * On load more add loader view
     */
    fun addLoadingView() {
        albumList.add(loaderView)
        notifyItemInserted(albumList.size - 1)
    }

    /**
     * When request result received remove loader view
     */
    private fun removeLoadingView() {
        val positionOfLoader = albumList.indexOf(loaderView)
        if (positionOfLoader > -1) {
            albumList.remove(loaderView)
        }
    }

    /**
     * Return true if waiting request result
     */
    fun isLoadingData(): Boolean = albumList.contains(loaderView)

    /**
     * Update list when request result received
     */
    fun onNewAlbumsListLoaded(newAlbumsList: List<Any>) {
        removeLoadingView()
        if (newAlbumsList.isNotEmpty()) {
            albumList.addAll(newAlbumsList)
        }
        notifyDataSetChanged()
    }
}