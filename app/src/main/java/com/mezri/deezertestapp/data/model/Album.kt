package com.mezri.deezertestapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.mezri.deezertestapp.data.network.dto.AlbumDTO
import com.mezri.deezertestapp.data.network.dto.UNKNOWN_VALUE

data class Album(val id: Int, val title: String, val cover: String, val artist: Artist) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readParcelable<Artist>(Artist::class.java.classLoader)!!
    )

    constructor(albumDTO: AlbumDTO) : this(albumDTO.id, albumDTO.title, albumDTO.cover_big, Artist(albumDTO.artist))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(cover)
        parcel.writeParcelable(artist, 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}