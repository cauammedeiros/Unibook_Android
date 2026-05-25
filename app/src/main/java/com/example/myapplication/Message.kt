package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class Message(
    val text: String,
    val isUser: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeByte(if (isUser) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message = Message(parcel)
        override fun newArray(size: Int): Array<Message?> = arrayOfNulls(size)
    }
}
