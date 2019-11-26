package com.krucha.kotlinsample.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krucha.kotlinsample.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class SimpleDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao


    companion object {
        private const val DATABASE = "simpleDatabase"

        @Volatile
        private var INSTANCE: SimpleDatabase? = null

        fun getInstance(context: Context) : SimpleDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, SimpleDatabase::class.java, DATABASE)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}