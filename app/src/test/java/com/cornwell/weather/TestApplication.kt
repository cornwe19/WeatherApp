package com.cornwell.weather

import com.cornwell.weather.components.DaggerNetworkComponent
import com.cornwell.weather.components.NetworkModule
import com.cornwell.weather.components.SchedulerModule
import com.cornwell.weather.data.WeatherApi
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.mockito.Mockito.mock
import retrofit2.Retrofit

class TestApplication : WeatherApplication() {
    override fun onCreate() {
        super.onCreate()
        network = DaggerNetworkComponent.builder()
                .schedulerModule(TestSchedulerModule())
                .networkModule(TestNetworkModule())
                .build()
    }

    private class TestSchedulerModule : SchedulerModule() {
        override fun provideMainScheduler(): Scheduler = Schedulers.trampoline()
        override fun provideBackgroundScheduler(): Scheduler = Schedulers.trampoline()
    }

    private class TestNetworkModule : NetworkModule() {
        override fun provideWeatherApi(retrofit: Retrofit): WeatherApi = weatherApi
    }

    companion object {
        val weatherApi: WeatherApi = mock(WeatherApi::class.java)
    }
}