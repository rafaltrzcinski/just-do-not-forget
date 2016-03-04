package pl.trzcinskiraf.justdonotforget.domain

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Note(
        val title: String,
        val content: String,
        val uuid: String = randomUUID()) : Parcelable {

    companion object {
        fun randomUUID() = UUID.randomUUID().toString()
        @JvmField final val CREATOR = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel) =
                    Note(title = source.readString(), content = source.readString())
            override fun newArray(size: Int) = arrayOfNulls<Note>(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
    }
}