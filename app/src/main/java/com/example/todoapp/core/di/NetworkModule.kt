package com.example.tprs.di.module

import android.content.Context
import com.example.todoapp.R
import com.example.todoapp.data.remote.ApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import java.security.KeyStore
import java.security.cert.Certificate
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 16.December.2022:09:56

///////////////////////////////////////////////////////////////////////////////////////////////
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val timeOutDurationInSeconds = 30L


    @Singleton
    @Provides
    fun provideKeyStore(
        certificate: Certificate
    ): KeyStore {
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null,null)
        keyStore.setCertificateEntry("certificate",certificate)
        return keyStore
    }

    @Singleton
    @Provides
    fun provideTrustManager(
        keyStore: KeyStore
    ): X509TrustManager {
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        trustManagerFactory.init(keyStore)

        val trustManagers = trustManagerFactory.trustManagers

        return trustManagers[0] as X509TrustManager
    }

    @Singleton
    @Provides
    fun provideSSLContext(
        trustManager: X509TrustManager
    ): SSLContext {

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trustManager), null)

        return sslContext
    }

    @Singleton
    @Provides
    fun provideHTTPClient(
        sslContext: SSLContext,
        trustManager: X509TrustManager
    ): OkHttpClient {

        return OkHttpClient
            .Builder()
            .sslSocketFactory(
                sslContext.socketFactory,
                trustManager
            )
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
            .baseUrl("BASE_URL")
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