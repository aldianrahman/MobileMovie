package com.example.mobilemovie.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Transaction

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "password") val password : String,
    @ColumnInfo(name = "gambar") val gambar : String

)
