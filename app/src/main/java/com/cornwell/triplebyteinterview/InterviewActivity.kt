package com.cornwell.triplebyteinterview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_interview.*
import kotlinx.android.synthetic.main.content_interview.*
import java.util.*

class InterviewActivity : AppCompatActivity() {
    val sampleTransactions = listOf(
            Transaction("Expenses", "Bought Groceries", 125.00, Date(1532393495000)),
            Transaction("Fun", "Went on a trip", 1500.00, Date(1532307095000)),
            Transaction("Expenses", "Paid Mortgage", 1200.00, Date(1532393495000))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)
        setSupportActionBar(toolbar)

        val transactionAdapter = TransactionAdapter()
        transactions.adapter = transactionAdapter
        transactionAdapter.setTransactions(sampleTransactions)

        fab.setOnClickListener { _ ->
            transactionAdapter.addTransaction(Transaction("ABC", "Bought a lambo", 130000.00, Date(1532047895000)))
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
