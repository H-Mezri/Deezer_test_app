package com.mezri.deezertestapp.ui.albumdetails

import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.ui.base.BaseViewModel

class AlbumDetailsFragmentViewModel : BaseViewModel() {

    // album
    lateinit var album: Album

    fun initAlbum(album: Album) {
        this.album = album
    }
}
