package com.lsimanenka.financetracker.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val EXPENSES = "expenses"
    const val EXPENSES_HISTORY = "expenses_history"
    const val INCOME = "income"
    const val INCOME_HISTORY = "income_history"
    const val ACCOUNT = "account"
    const val ITEMS = "items"
    const val SETTINGS = "settings"
    const val ACCOUNT_EDIT = "account_edit"
    const val TRANSACTION_CREATE = "transaction_create"
    const val TRANSACTION_EDIT     = "transaction_edit"
    const val TRANSACTION_EDIT_ROUTE = "$TRANSACTION_EDIT/{transactionId}"

    fun navToEditTransaction(id: Int) = "transaction_edit/$id"
}
