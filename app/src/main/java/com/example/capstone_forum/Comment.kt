package com.example.capstone_forum

import android.os.Parcel
import android.os.Parcelable

class Comment(
    var id: String, var creator: String, var timeCreated: Long,
    var comment: String, var likeRatio: Int
) : Parcelable, Comparable<Comment> {

    constructor(): this("", "", 0, "", 0)

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(creator)
        parcel.writeLong(timeCreated)
        parcel.writeString(comment)
        parcel.writeInt(likeRatio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: Comment): Int {
        return this.timeCreated.compareTo(other.timeCreated)
    }
}