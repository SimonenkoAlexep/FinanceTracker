package com.lsimanenka.financetracker.ui.utils.list_item

import android.annotation.SuppressLint
import android.util.Log
import com.lsimanenka.financetracker.data.model.TransactionResponse
import java.time.LocalDate
import kotlin.math.exp

data class DailyDiff(
    val date: LocalDate,
    val diff: Double
)

@SuppressLint("NewApi")
fun calculateDailyDiffs(transactions: List<TransactionResponse>): List<DailyDiff> {
    val today = LocalDate.now()
    val last30Days = (0..29).map { today.minusDays(it.toLong()) }

    val grouped = transactions.groupBy { LocalDate.parse(it.transactionDate.substring(0, 10)) }

    return last30Days.reversed().map { day ->
        val txs = grouped[day].orEmpty()
        val income = txs.filter { it.amount.toDoubleOrNull() ?: 0.0 >= 0 }.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
        val expense = txs.filter { it.amount.toDoubleOrNull() ?: 0.0 < 0 }.sumOf { it.amount.toDoubleOrNull()?.let { -it } ?: 0.0 }
        Log.d("DIFFF", "${income-expense} $income ${expense}")
        DailyDiff(date = day, diff = income - expense)
    }
}
