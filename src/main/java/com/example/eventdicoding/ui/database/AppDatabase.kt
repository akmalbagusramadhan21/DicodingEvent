package com.example.eventdicoding.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEvent::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,"event_database")
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}