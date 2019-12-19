package com.krucha.kotlinsample.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.krucha.kotlinsample.CustomLog
import com.krucha.kotlinsample.data.TestData
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Film::class], version = 2, exportSchema = false)
abstract class SimpleDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun filmDao() : FilmDao

    private class DatabaseCallback : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let {db ->
                GlobalScope.launch(Dispatchers.IO) {
                    firstFillDatabase(db.userDao(), db.filmDao())
                }
            }
        }

        private suspend fun firstFillDatabase(userDao: UserDao, filmDao: FilmDao) {
            CustomLog("DB").debug("Fill database")

            userDao.insert(TestData.user)
            filmDao.insert(TestData.film1)
            filmDao.insert(TestData.film2)
        }
    }

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
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}