package com.example.splitbill

import android.os.Parcel
import android.os.Parcelable

data class SplitParticipant (
    val pId: Int,
    val name: String,
    val amount: String,
    var paidStatus: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pId)
        parcel.writeString(name)
        parcel.writeString(amount)
        parcel.writeByte(if (paidStatus) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SplitParticipant> {
        override fun createFromParcel(parcel: Parcel): SplitParticipant {
            return SplitParticipant(parcel)
        }

        override fun newArray(size: Int): Array<SplitParticipant?> {
            return arrayOfNulls(size)
        }
    }
}
