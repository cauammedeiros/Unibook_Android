package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class Message(
    val text: String,
    val isUser: Boolean,
    var livroId: String? = null,
    var livroTitulo: String? = null,
    var livroAutor: String? = null,
    var livroGenero: String? = null,
    var livroSinopse: String? = null,
    var livroCapaUrl: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeByte(if (isUser) 1 else 0)
        parcel.writeString(livroId)
        parcel.writeString(livroTitulo)
        parcel.writeString(livroAutor)
        parcel.writeString(livroGenero)
        parcel.writeString(livroSinopse)
        parcel.writeString(livroCapaUrl)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message = Message(parcel)
        override fun newArray(size: Int): Array<Message?> = arrayOfNulls(size)
    }
}
