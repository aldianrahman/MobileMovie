package com.example.mobilemovie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coming(
    @SerializedName("id")
    val id : String?,

    @SerializedName("title")
    val title : String,

    @SerializedName("poster_path")
    val poster : String?,

    @SerializedName("overview")
    val desc : String?,

    @SerializedName("release_date")
    val star : String?


): Parcelable {
    constructor():this("","","","","")
}