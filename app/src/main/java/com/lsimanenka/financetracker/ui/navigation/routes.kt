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
    const val ACCOUNT_EDIT_BASE = "account_edit"
    const val ACCOUNT_EDIT_ARG = "$ACCOUNT_EDIT_BASE/{accountId}"

    fun accountEdit(accountId: Int) = "$ACCOUNT_EDIT_BASE/$accountId"
}
