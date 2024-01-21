package com.example.tprs.di.module

import com.example.tprs.data.SandsBluetooth
import com.example.tprs.data.SandsLocation
import com.example.tprs.data.SandsSharedPreferences
import com.example.tprs.implementation.BluetoothImpl
import com.example.tprs.implementation.LocationImpl
import com.example.tprs.implementation.SharedPreferencesImpl
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
    ): SandsSharedPreferences

    @Binds
    abstract fun provideSandsBluetooth(
        impl: BluetoothImpl
    ): SandsBluetooth

    @Binds
    abstract fun provideSandsLocation(
        impl: LocationImpl
    ): SandsLocation
}