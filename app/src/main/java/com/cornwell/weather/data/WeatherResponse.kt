package com.cornwell.weather.data

import java.util.*

data class WeatherResponse(val query: QueryResult) {
    val location; get() = query.results.channel.location
    val item; get() = query.results.channel.item
}
data class QueryResult(val results: Results) 
data class Results(val channel: Channel)
data class Channel(val location: Location, val item: Item)
data class Location(val city: String, val country: String, val region: String)
data class Item(val condition: Condition)
data class Condition(val text: String, val temp: String, val date: Date)
