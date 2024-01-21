package com.example.todoapp.core.di

import com.example.todoapp.core.implementation.SharedPreferencesImpl
import com.example.todoapp.data.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 06.January.2023:14:04

///////////////////////////////////////////////////////////////////////////////////////////////

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    abstract fun provideSharedPreferences(
        impl: SharedPreferencesImpl
    ): SharedPreferences

}