package com.cornwell.weather

import android.app.Application
import com.cornwell.weather.components.DaggerNetworkComponent
import com.cornwell.weather.components.NetworkComponent

open class WeatherApplication : Application() {
    lateinit var network: NetworkComponent; protected set

    override fun onCreate() {
        super.onCreate()
        sApp = this
        network = DaggerNetworkComponent.builder().build()
    }

    companion object {
        lateinit var sApp: WeatherApplication; private set
    }
}