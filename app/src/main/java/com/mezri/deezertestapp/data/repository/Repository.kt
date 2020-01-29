package com.mezri.deezertestapp.data.repository

import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.model.RequestData
import io.reactivex.Observable

interface Repository {
    /**
     * get albums from remote server
     */
    fun getAlbums(index: Int) : Observable<RequestData<Album>>
}