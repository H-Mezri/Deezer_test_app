package com.mezri.deezertestapp.ui

import android.view.View
import com.mezri.deezertestapp.data.model.Album

interface MainActivityInterface {
    fun showAlbumDetailsFragment(view: View, album: Album)
}