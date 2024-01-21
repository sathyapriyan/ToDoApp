package com.example.tprs.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 16.December.2022:10:03

///////////////////////////////////////////////////////////////////////////////////////////////

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Singleton
    @Provides
    @Named("ioDispatcher")
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    @Named("mainDispatcher")
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Singleton
    @Provides
    @Named("defaultDispatcher")
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}