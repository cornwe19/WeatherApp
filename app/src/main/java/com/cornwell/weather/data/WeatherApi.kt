package com.cornwell.weather.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("public/yql")
    fun makeQuery(
            @Query("q") query: String,
            @Query("format") format: String = "json"
    ): Observable<WeatherResponse>

    enum class Location(val woeid: Int) {
        ROCHESTER(2482949),
        SAN_DIEGO(2487889)
    }
    
    companion object {
        const val TIME_FORMAT = "EEE, dd MMM yyyy hh:mm aa zzz"

        fun conditionsAtLocation(location: Location) = conditionsAtLocation(location.woeid)

        // TODO YQL Query builder
        fun conditionsAtLocation(woeid: Int) =
                "select location, item.condition " +
                "from weather.forecast " +
                "where woeid=$woeid"
    }
}