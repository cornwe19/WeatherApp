package com.cornwell.triplebyteinterview

import android.app.Application
import com.cornwell.triplebyteinterview.components.DaggerNetworkComponent
import com.cornwell.triplebyteinterview.components.NetworkComponent

open class InterviewApplication : Application() {
    lateinit var network: NetworkComponent; protected set

    override fun onCreate() {
        super.onCreate()
        app = this
        network = DaggerNetworkComponent.builder().build()
    }

    companion object {
        lateinit var app: InterviewApplication; private set
    }
}