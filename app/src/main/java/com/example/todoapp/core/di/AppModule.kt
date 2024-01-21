package com.example.tprs.di.module

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import com.example.todoapp.data.remote.ApiDataSource
import com.example.todoapp.data.repository.DataRepository
import com.example.todoapp.data.room.dao.ToDoDataDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataRepository(
        dataApi: ApiDataSource,
        toDoDataDao: ToDoDataDao,
    ): DataRepository {
        return DataRepository(dataApi = dataApi, dataDao = toDoDataDao)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

}