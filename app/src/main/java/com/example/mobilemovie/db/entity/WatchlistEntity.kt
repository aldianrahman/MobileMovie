package com.example.mobilemovie.db.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "value") val value : String,
    @ColumnInfo(name = "iduser") val iduser : Int?,
    @ColumnInfo(name = "gambar") val gambar : String

)

