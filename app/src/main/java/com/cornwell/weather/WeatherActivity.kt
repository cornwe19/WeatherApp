package com.cornwell.weather

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cornwell.weather.data.WeatherApi
import com.cornwell.weather.data.WeatherApi.Location
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.content_weather.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Named

class WeatherActivity : AppCompatActivity() {
    @Inject internal lateinit var weatherApi: WeatherApi
    @Inject @field:Named("main") internal lateinit var mainScheduler: Scheduler
    @Inject @field:Named("background") internal lateinit var backgroundScheduler: Scheduler

    private val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        setSupportActionBar(toolbar)

        WeatherApplication.sApp.network.inject(this)

        fab.setOnClickListener { _ ->
            weatherApi.makeQuery(WeatherApi.conditionsAtLocation(Location.ROCHESTER))
                    .subscribeOn(backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribe({
                        location.text = "${it.location.city}, ${it.location.region}"
                        temperature.text = "${it.item.condition.temp}°"
                        condition.text = it.item.condition.text
                        date.text = dateFormat.format(it.item.condition.date)
                    }, {
                        Snackbar.make(root, it.message ?: "Failed fetching weather", Snackbar.LENGTH_LONG).show()
                    })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_interview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
