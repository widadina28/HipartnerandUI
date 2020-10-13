package com.ros.belajarbaseactivity.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DataClassProject::class],
    version = 1
)
abstract class ProjectDB : RoomDatabase(){

    abstract fun dao() : Dao

    companion object {

        @Volatile private var instance : ProjectDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ProjectDB::class.java,
            "note12345.db"
        ).build()

    }
}
