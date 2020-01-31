package com.mezri.deezertestapp.data.repository

import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.network.AlbumsAPI
import com.mezri.deezertestapp.data.network.RetrofitClient
import com.mezri.deezertestapp.data.network.dto.AlbumDTO
import com.mezri.deezertestapp.data.network.dto.DTOMapper
import com.mezri.deezertestapp.data.network.dto.RequestDTO
import io.reactivex.Single

class RepositoryImpl :
    Repository {

    private val networkService by lazy {
        RetrofitClient.getNetworkClient().create(AlbumsAPI::class.java)
    }

    /**
     * Get albums from remote server
     * /!\ /!\ In a bigger project repository must check if the data already exist in a local database an return it
     * if the data not found in local database or data is old, then trigger network request then save the result in local database
     */
    override fun getAlbums(index: Int): Single<List<Album>> {
        return networkService.loadAlbums(index).map {
            mapAlbums(it)
        }
    }

    private fun mapAlbums(networkAlbumsList: RequestDTO<AlbumDTO>): List<Album> {
        val albumDataMapper: DTOMapper<AlbumDTO, Album> =
            object : DTOMapper<AlbumDTO, Album> {
                override fun map(input: AlbumDTO): Album {
                    return Album(input)
                }
            }

        return networkAlbumsList.data.map { albumDataMapper.map(it) }
    }
}