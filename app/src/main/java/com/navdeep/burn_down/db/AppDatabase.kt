package com.navdeep.burn_down.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteDataClass::class, ProfileDataClass::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun deviceDao(): RepoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "burn_out"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build() // <---- The crash occurs here
                INSTANCE = instance
                return instance
            }
        }
    }
}