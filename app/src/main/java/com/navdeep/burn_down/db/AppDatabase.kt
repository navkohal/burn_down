package com.navdeep.burn_down.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteDataClass::class], version = 1, exportSchema = false)
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
                    "burn_down"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build() // <---- The crash occurs here
                INSTANCE = instance
                return instance
            }
        }
    }

//    companion object {
//
//        private var appDatabase: AppDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): AppDatabase {
//            if (appDatabase == null) {
//                appDatabase =
//                    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"burn_down")
//                        .allowMainThreadQueries()
//                        .fallbackToDestructiveMigration()
//                        .build()
//            }
//            return appDatabase!!
//        }
//    }


}