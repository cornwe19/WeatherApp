package com.cornwell.triplebyteinterview

import android.app.Application
import com.cornwell.triplebyteinterview.components.DaggerNetworkComponent
import com.cornwell.triplebyteinterview.components.NetworkComponent
import com.cornwell.triplebyteinterview.components.NetworkModule

class InterviewApplication : Application() {
    lateinit var network: NetworkComponent; private set

    override fun onCreate() {
        super.onCreate()
        app = this
        network = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule())
                .build()
    }

    companion object {
        lateinit var app: InterviewApplication; private set
    }
}