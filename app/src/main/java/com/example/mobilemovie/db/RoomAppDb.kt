package com.example.mobilemovie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobilemovie.db.entity.FavoriteEntity
import com.example.mobilemovie.db.entity.UserEntity
import com.example.mobilemovie.db.entity.WatchlistEntity
import com.example.mobilemovie.db.entity.relation.ImageEntity

//@Database(
//    entities = [WatchlistEntity::class,
//               FavoriteEntity::class],
//    version = 1
//)
//abstract class RoomAppDb : RoomDatabase(){
//
//    abstract fun watchlistDao() : WatchlistDao
////    abstract fun favDao():FavoriteDao
//
//    companion object {
//
//        @Volatile private var instance : RoomAppDb? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            RoomAppDb::class.java,
//            "aldianrahman.db"
//        ).build()
//    }
//
//
//
//}

@Database(
    entities = [
        WatchlistEntity::class,
        FavoriteEntity::class,
        UserEntity::class,
    ImageEntity::class
    ],
    version = 1
)
abstract class RoomAppDb : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao

    companion object {
        @Volatile
        private var INSTANCE: RoomAppDb? = null

        operator fun invoke(context: Context): RoomAppDb {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    "school_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
