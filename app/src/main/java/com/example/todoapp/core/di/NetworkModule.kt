package com.example.todoapp.core.di

import com.example.todoapp.data.remote.ApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val timeOutDurationInSeconds = 30L

    @Singleton
    @Provides
    fun provideHTTPClient(): OkHttpClient {

        return OkHttpClient
            .Builder()
            .readTimeout(timeOutDurationInSeconds, TimeUnit.SECONDS)
            .connectTimeout(timeOutDurationInSeconds, TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            //.baseUrl(BuildConfig.BASE_URL)
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    }


    @Singleton
    @Provides
    fun provideDataApi(
        retrofit: Retrofit
    ): ApiDataSource {
        return retrofit.create(ApiDataSource::class.java)
    }

}