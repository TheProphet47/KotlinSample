package com.krucha.kotlinsample.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.krucha.kotlinsample.data.TestData
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.User
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@Database(entities = [User::class, Film::class], version = 1, exportSchema = false)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun filmDao() : FilmDao

    companion object {
        const val DB_NAME = "simpleDatabase"
    }


    class DatabaseCallback @Inject constructor(private val lazyRoomDb: Lazy<SampleDatabase>) : RoomDatabase.Callback() {

        private val roomDb: SampleDatabase by lazy { lazyRoomDb.get() }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            GlobalScope.launch(Dispatchers.IO) {
                firstFillDatabase(roomDb.userDao(), roomDb.filmDao())
            }
        }

        private suspend fun firstFillDatabase(userDao: UserDao, filmDao: FilmDao) {
            userDao.insert(TestData.user)
            filmDao.insert(TestData.film1)
            filmDao.insert(TestData.film2)
        }
    }
}