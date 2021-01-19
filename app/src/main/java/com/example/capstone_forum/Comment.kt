package com.example.capstone_forum

import android.os.Parcel
import android.os.Parcelable

class Comment(var creator: String, var timeCreated: Long,
    var comment: String
) : Parcelable, Comparable<Comment> {

    constructor(): this( "", 0, "")

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(creator)
        parcel.writeLong(timeCreated)
        parcel.writeString(comment)
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