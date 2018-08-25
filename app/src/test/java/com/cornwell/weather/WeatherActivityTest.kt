package com.cornwell.weather

import android.widget.TextView
import com.cornwell.weather.data.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.content_weather.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class WeatherActivityTest {

    @JvmField @Rule val verifier = ErrorCollector()

    @Test
    fun whenLoadWeather_willDisplayCurrentConditions() {
        `when`(TestApplication.weatherApi.makeQuery(anyString(), anyString())).thenReturn(
                Observable.just(ResponseBuilder()
                        .withCondition(Condition("Sunny", "45", "8/24/18 12:31 PM".asDate()))
                        .build()))

        val activity = buildActivity(WeatherActivity::class.java).create().get()

        activity.fab.performClick()

        verifier.checkThat(activity.condition.text, equalTo<CharSequence>("Sunny"))
        verifier.checkThat(activity.temperature.text, equalTo<CharSequence>("45°"))
        verifier.checkThat(activity.date.text, equalTo<CharSequence>("8/24/18 12:31 PM"))
    }
    
    @Test
    fun whenLoadWeather_willDisplayCurrentLocation() {
        `when`(TestApplication.weatherApi.makeQuery(anyString(), anyString())).thenReturn(
                Observable.just(ResponseBuilder()
                        .withLocation(Location("Rochester", "US", "NY"))
                        .build()))

        val activity = buildActivity(WeatherActivity::class.java).create().get()

        activity.fab.performClick()

        assertThat(activity.location.text, equalTo<CharSequence>("Rochester, NY"))
    }
    
    @Test
    fun whenLoadWeatherFails_willShowError() {
        `when`(TestApplication.weatherApi.makeQuery(anyString(), anyString())).thenReturn(
                Observable.error(IOException("API limit hit")))

        val activity = buildActivity(WeatherActivity::class.java).create().get()

        activity.fab.performClick()

        assertThat(activity.findViewById<TextView>(R.id.snackbar_text).text, equalTo<CharSequence>("API limit hit"))
    }

    private fun String.asDate() = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).parse(this)

    private class ResponseBuilder {
        var condition: Condition = Condition("", "", Date())
        var location: Location = Location("", "", "")

        fun withLocation(location: Location) = apply {
            this.location = location
        }

        fun withCondition(condition: Condition) = apply { 
            this.condition = condition
        }

        fun build() = WeatherResponse(QueryResult(Results(Channel(location, Item(condition)))))
    }
}