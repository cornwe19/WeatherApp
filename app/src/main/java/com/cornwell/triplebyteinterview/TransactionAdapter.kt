package com.cornwell.triplebyteinterview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.transaction.view.*
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*

val DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT)

class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_TRANSACTION = 2
    }

    init {
        setHasStableIds(true)
    }

    private val transactions: List<Any>; get() {
        return transactionsCategorized.flatMap {
            val flattened = ArrayList<Any>()
            flattened.add(it.key)
            flattened.addAll(it.value)
            flattened
        }
    }
    private val transactionsCategorized = TreeMap<String, MutableList<Transaction>>()

    fun setTransactions(transactions: List<Transaction>) {
        transactions.forEach {
            addTransactionInternal(it)
        }

        notifyDataSetChanged()
    }

    private fun addTransactionInternal(it: Transaction) {
        val formattedDate = DATE_FORMAT.format(it.date)
        if (transactionsCategorized[formattedDate] == null) {
            transactionsCategorized[formattedDate] = ArrayList()
        }

        transactionsCategorized[formattedDate]?.add(it)
    }

    fun addTransaction(transaction: Transaction) {
        addTransactionInternal(transaction)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return transactions[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_TRANSACTION -> {
                TransactionViewHolder(inflater.inflate(R.layout.transaction, parent, false))
            }
            else -> HeaderViewHolder(inflater.inflate(R.layout.header, parent, false))
        }
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> holder.bind(transactions[position] as Transaction)
            is HeaderViewHolder -> holder.itemView.label.text = transactions[position].toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(transactions[position]) {
            is Transaction -> TYPE_TRANSACTION
            else -> TYPE_HEADER
        }
    }
}

class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(transaction: Transaction) {
        itemView.amount.text = NumberFormat.getCurrencyInstance().format(transaction.amount)
        itemView.summary.text = transaction.summary
        itemView.date.text = DATE_FORMAT.format(transaction.date)
    }
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)