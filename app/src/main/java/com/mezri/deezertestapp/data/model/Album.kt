package com.mezri.deezertestapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Album (
	@Json(name ="id") val id : Int,
	@Json(name ="title") val title : String,
	@Json(name ="link") val link : String,
	@Json(name ="cover") val cover : String,
	@Json(name ="cover_small") val cover_small : String,
	@Json(name ="cover_medium") val  cover_medium  : String,
	@Json(name ="cover_big") val cover_big : String,
	@Json(name ="cover_xl") val  cover_xl  : String,
	@Json(name ="nb_tracks") val  nb_tracks  : Int,
	@Json(name ="release_date") val  release_date  : String,
	@Json(name ="record_type") val record_type : String,
	@Json(name ="available") val available : Boolean,
	@Json(name ="tracklist") val tracklist : String,
	@Json(name ="explicit_lyrics") val  explicit_lyrics  : Boolean,
	@Json(name ="time_add") val  time_add  : Int,
	@Json(name ="artist") var  artist  :  Artist?,
	@Json(name ="type") val  type  : String
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
		parcel.readInt(),
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readByte() != 0.toByte(),
		parcel.readString()!!,
		parcel.readByte() != 0.toByte(),
		parcel.readInt(),
		parcel.readParcelable(Artist.javaClass.classLoader),
		parcel.readString()!!
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeString(title)
		parcel.writeString(link)
		parcel.writeString(cover)
		parcel.writeString(cover_small)
		parcel.writeString(cover_medium)
		parcel.writeString(cover_big)
		parcel.writeString(cover_xl)
		parcel.writeInt(nb_tracks)
		parcel.writeString(release_date)
		parcel.writeString(record_type)
		parcel.writeByte(if (available) 1 else 0)
		parcel.writeString(tracklist)
		parcel.writeByte(if (explicit_lyrics) 1 else 0)
		parcel.writeInt(time_add)
		parcel.writeParcelable(artist, 0)
		parcel.writeString(type)
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