package com.mezri.deezertestapp.data.network.dto

import com.squareup.moshi.Json

data class ArtistDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "picture") val picture: String,
    @Json(name = "picture_small") val picture_small: String,
    @Json(name = "picture_medium") val picture_medium: String,
    @Json(name = "picture_big") val picture_big: String,
    @Json(name = "picture_xl") val picture_xl: String,
    @Json(name = "tracklist") val trackList: String,
    @Json(name = "type") val type: String
)