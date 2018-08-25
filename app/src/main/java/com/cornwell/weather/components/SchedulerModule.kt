package com.cornwell.weather.components

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
open class SchedulerModule {

    @Provides
    @Named("main")
    open fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
    
    @Provides
    @Named("background")
    open fun provideBackgroundScheduler(): Scheduler = Schedulers.io()
}