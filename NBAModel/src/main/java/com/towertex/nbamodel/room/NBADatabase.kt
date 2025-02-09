package com.towertex.nbamodel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.model.TeamItem

@Database(
    entities = [
        PlayerItem::class,
        TeamItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NBADatabase : RoomDatabase() {
    abstract val nbaDao: NBADao

    companion object {
        private const val DATABASE_NAME = "NBADatabase"

        fun buildDatabase(context: Context): NBADatabase = Room
            .databaseBuilder(context, NBADatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}