package com.example.capstone_forum

import android.os.Parcel
import android.os.Parcelable

class Post(
    var id: String,
    var category: String,
    var creator: String,
    var creatorID: String,
    var timeCreated: Long,
    var title: String,
    var description: String,
    var imageUrl: String?,
    var comments: ArrayList<Comment>,
    var likedOrNot: HashMap<String, Boolean>
) : Parcelable, Comparable<Post> {

    constructor() : this("", "", "", "", 0, "", "", "", arrayListOf(), hashMapOf())

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Comment.CREATOR)!!,
        hashMapOf<String, Boolean>().apply {
            parcel.readHashMap(this.javaClass.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(category)
        parcel.writeString(creator)
        parcel.writeString(creatorID)
        parcel.writeLong(timeCreated)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
        parcel.writeTypedList(comments)
        parcel.writeMap(likedOrNot)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun compareTo(other: Post): Int {
        return this.timeCreated.compareTo(other.timeCreated)
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}