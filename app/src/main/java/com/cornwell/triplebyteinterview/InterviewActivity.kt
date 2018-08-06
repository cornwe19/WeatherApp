package com.cornwell.triplebyteinterview

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cornwell.triplebyteinterview.data.WeatherApi
import com.cornwell.triplebyteinterview.data.WeatherApi.Location
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_interview.*
import kotlinx.android.synthetic.main.content_interview.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat

class InterviewActivity : AppCompatActivity() {
    private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("EEE, dd MMM yyyy hh:mm aa zzz").create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    private val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            retrofit.create(WeatherApi::class.java)
                    .makeQuery(WeatherApi.conditionsAtLocation(Location.SAN_DIEGO))
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
