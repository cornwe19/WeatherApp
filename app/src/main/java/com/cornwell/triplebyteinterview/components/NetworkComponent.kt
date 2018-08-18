package com.cornwell.triplebyteinterview.components

import com.cornwell.triplebyteinterview.InterviewActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SchedulerModule::class])
interface NetworkComponent {
    fun inject(activity: InterviewActivity)
}