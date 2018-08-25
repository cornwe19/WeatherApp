package com.cornwell.weather.components

import com.cornwell.weather.WeatherActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SchedulerModule::class])
interface NetworkComponent {
    fun inject(activity: WeatherActivity)
}