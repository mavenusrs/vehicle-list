package com.mavenusrs.vehicles.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Vehicle(
    val colour: String?,
    val description: String?,
    val firstRegistration: String?,
    val fuel: String?,
    val id: Int?,
    val images: List<Image>?,
    val make: String?,
    val mileage: Int?,
    val model: String?,
    val modelline: String?,
    val price: Int?,
    val seller: Seller?,
    var notes: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(Image),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Seller::class.java.classLoader),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(colour)
        parcel.writeString(description)
        parcel.writeString(firstRegistration)
        parcel.writeString(fuel)
        parcel.writeValue(id)
        parcel.writeTypedList(images)
        parcel.writeString(make)
        parcel.writeValue(mileage)
        parcel.writeString(model)
        parcel.writeString(modelline)
        parcel.writeValue(price)
        parcel.writeParcelable(seller, flags)
        parcel.writeStringList(notes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        override fun createFromParcel(parcel: Parcel): Vehicle {
            return Vehicle(parcel)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }
}