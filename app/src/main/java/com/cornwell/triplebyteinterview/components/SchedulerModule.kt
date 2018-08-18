package com.cornwell.triplebyteinterview.components

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {

    @Provides
    @Named("main")
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
    
    @Provides
    @Named("background")
    fun provideBackgroundScheduler(): Scheduler = Schedulers.io()
}