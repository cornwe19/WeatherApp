package com.cornwell.triplebyteinterview.components

import com.cornwell.triplebyteinterview.BuildConfig
import com.cornwell.triplebyteinterview.data.WeatherApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Singleton
    @Provides
    open fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().setDateFormat(WeatherApi.TIME_FORMAT).create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    open fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}