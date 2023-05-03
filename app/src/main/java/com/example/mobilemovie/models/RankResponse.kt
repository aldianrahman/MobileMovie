package com.example.mobilemovie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RankResponse(

    @SerializedName("results")
    val rank : List<Rank>
):Parcelable {
    constructor(): this(mutableListOf())
}