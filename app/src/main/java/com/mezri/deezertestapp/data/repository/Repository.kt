package com.mezri.deezertestapp.data.repository

import com.mezri.deezertestapp.data.model.Album
import io.reactivex.Single

interface Repository {
    /**
     * get albums from remote server
     */
    fun getAlbums(index: Int): Single<List<Album>>
}