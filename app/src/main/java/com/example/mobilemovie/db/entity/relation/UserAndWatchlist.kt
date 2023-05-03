package com.example.mobilemovie.db.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mobilemovie.db.entity.UserEntity
import com.example.mobilemovie.db.entity.WatchlistEntity

data class UserAndWatchlist(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userName",
        entityColumn = "userName"
    )
    val watchlist : WatchlistEntity
)