package com.example.businesstripsapp.create_office_activity.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [OfficeDB::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun officeDao(): OfficeDao?

    companion object {
        private var db: AppDatabase? = null
        fun build(context: Context): AppDatabase? {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return db
        }
    }
}