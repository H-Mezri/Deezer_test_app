package com.mezri.deezertestapp.data.network.dto

import com.squareup.moshi.Json

data class AlbumDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "link") val link: String,
    @Json(name = "cover") val cover: String,
    @Json(name = "cover_small") val cover_small: String,
    @Json(name = "cover_medium") val cover_medium: String,
    @Json(name = "cover_big") val cover_big: String,
    @Json(name = "cover_xl") val cover_xl: String,
    @Json(name = "nb_tracks") val nb_tracks: Int,
    @Json(name = "release_date") val release_date: String,
    @Json(name = "record_type") val record_type: String,
    @Json(name = "available") val available: Boolean,
    @Json(name = "tracklist") val tracklist: String,
    @Json(name = "explicit_lyrics") val explicit_lyrics: Boolean,
    @Json(name = "time_add") val time_add: Int,
    @Json(name = "artist") var artist: ArtistDTO,
    @Json(name = "type") val type: String
)