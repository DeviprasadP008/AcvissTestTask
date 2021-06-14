package com.AcvissTechnologies.acvisstesttask.database.AppDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.AcvissTechnologies.acvisstesttask.database.Dao.ScanDataDao
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel

@Database(entities = [ScanDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scandataDao(): ScanDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataseClient(context: Context) : AppDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "AcvissTestTaskDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }
        }
    }

}