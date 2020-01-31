package com.mezri.deezertestapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.mezri.deezertestapp.data.network.dto.ArtistDTO
import com.mezri.deezertestapp.data.network.dto.UNKNOWN_VALUE

data class Artist(val id: Int, val name: String, val picture: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE
    )

    constructor(artistDTO: ArtistDTO) : this(artistDTO.id, artistDTO.name, artistDTO.picture)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(picture)
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