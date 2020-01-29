package com.mezri.deezertestapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Artist(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "picture") val picture: String,
    @Json(name = "picture_small") val picture_small: String,
    @Json(name = "picture_medium") val picture_medium: String,
    @Json(name = "picture_big") val picture_big: String,
    @Json(name = "picture_xl") val picture_xl: String,
    @Json(name = "tracklist") val trackList: String,
    @Json(name = "type") val type: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(picture)
        parcel.writeString(picture_small)
        parcel.writeString(picture_medium)
        parcel.writeString(picture_big)
        parcel.writeString(picture_xl)
        parcel.writeString(trackList)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }

}