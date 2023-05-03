package com.example.mobilemovie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ComingResponse(

    @SerializedName("results")
    val coming : List<Coming>
):Parcelable {
    constructor(): this(mutableListOf())
}