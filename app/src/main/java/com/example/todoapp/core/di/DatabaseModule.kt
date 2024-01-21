package com.example.tprs.di.module

import android.content.Context
import androidx.room.Room
import com.example.todoapp.core.util.constants.DATABASE_NAME
import com.example.todoapp.data.room.dao.ToDoDataDao
import com.example.todoapp.data.room.db.DatabaseToDo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideSPRSDatabase(
        @ApplicationContext context: Context
    ): DatabaseToDo {

        return Room.databaseBuilder(
            context,
            DatabaseToDo::class.java,
            DATABASE_NAME
        ).build()

    }

    @Singleton
    @Provides
    fun provideMasterDao(
        database: DatabaseToDo
    ): ToDoDataDao {

        return database.toDoDataDao()

    }

}