package com.anibalbastias.android.marvelapp.module

import com.anibalbastias.android.marvelapp.BuildConfig
import com.anibalbastias.android.marvelapp.base.api.data.marvel.MarvelAPIGSONManager
import com.anibalbastias.android.marvelapp.base.api.data.marvel.MarvelApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class MarvelAPIModule {

    companion object {
        private const val CONNECT_TIMEOUT = 120L
        private const val READ_TIMEOUT = 120L
        private const val WRITE_TIMEOUT = 120L
        private var BASE_ENDPOINT: String = ""
    }


    @Provides
    @Singleton
    @Named("provideRetrofitHttpClient")
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    @Named("provideMarvelRetrofit")
    fun provideRetrofit(@Named("provideRetrofitHttpClient") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(makeGson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun makeGson(): Gson {
        return MarvelAPIGSONManager.createGsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    fun provideMarvelAPI(@Named("provideMarvelRetrofit") retrofit: Retrofit): MarvelApiService =
        retrofit.create(MarvelApiService::class.java)
}