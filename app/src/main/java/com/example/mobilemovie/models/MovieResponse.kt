package com.example.mobilemovie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(

    @SerializedName("results")
    val movie : List<Movie>
):Parcelable{
    constructor(): this(mutableListOf())
}

