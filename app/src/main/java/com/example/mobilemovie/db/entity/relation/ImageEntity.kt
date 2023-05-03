package com.example.mobilemovie.db.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idimg") val idimg : Int,
    @ColumnInfo(name = "img") val img:String,
    @ColumnInfo(name = "iduser") val iduser :Int

)
