package com.mezri.deezertestapp.data.network

import com.mezri.deezertestapp.data.network.dto.AlbumDTO
import com.mezri.deezertestapp.data.network.dto.RequestDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsAPI {
    /**
     * Get request for albums
     */
    @GET("2.0/user/2529/albums")
    fun loadAlbums(@Query("index") index: Int): Single<RequestDTO<AlbumDTO>>
}