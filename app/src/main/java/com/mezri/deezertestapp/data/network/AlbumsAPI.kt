package com.mezri.deezertestapp.data.network

import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.model.RequestData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsAPI {
    /**
     * Get request for albums
     */
    @GET("2.0/user/2529/albums")
    fun loadAlbums(@Query("index") index: Int): Observable<RequestData<Album>>
}