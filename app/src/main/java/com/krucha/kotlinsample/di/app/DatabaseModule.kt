package com.krucha.kotlinsample.di.app

import android.content.Context
import androidx.room.Room
import com.krucha.kotlinsample.data.room.SampleDatabase
import com.krucha.kotlinsample.di.scope.PerApp
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides @PerApp
    fun provideFilmDao(database: SampleDatabase) = database.filmDao()

    @Provides @PerApp
    fun provideUserDao(database: SampleDatabase) = database.userDao()

    @Provides @PerApp
    fun provideDatabase(context: Context, dbCallback: SampleDatabase.DatabaseCallback): SampleDatabase {
        return Room.databaseBuilder(context, SampleDatabase::class.java, SampleDatabase.DB_NAME)
            .addCallback(dbCallback)
            .fallbackToDestructiveMigration()
            .build()
    }
}