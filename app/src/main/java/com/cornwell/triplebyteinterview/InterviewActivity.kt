package com.cornwell.triplebyteinterview

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cornwell.triplebyteinterview.data.WeatherApi
import com.cornwell.triplebyteinterview.data.WeatherApi.Location
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_interview.*
import kotlinx.android.synthetic.main.content_interview.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Inject

class InterviewActivity : AppCompatActivity() {
    @Inject
    internal lateinit var weatherApi: WeatherApi
    private val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)
        setSupportActionBar(toolbar)

        InterviewApplication.app.network.inject(this)

        fab.setOnClickListener { _ ->
            weatherApi.makeQuery(WeatherApi.conditionsAtLocation(Location.ROCHESTER))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        location.text = "${it.location.city}, ${it.location.region}"
                        temperature.text = "${it.item.condition.temp}°"
                        condition.text = it.item.condition.text
                        date.text = dateFormat.format(it.item.condition.date)
                    }, {
                        Snackbar.make(root, "Failed fetching weather", Snackbar.LENGTH_LONG).show()
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
