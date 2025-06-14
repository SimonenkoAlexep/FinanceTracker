package com.lsimanenka.financetracker.domain_models

data class AccountCreateRequest(
    val balance: String,
    val currency: String,
    val name: String
)

data class AccountUpdateRequest(
    val balance: String,
    val currency: String,
    val name: String
)

class Account : ArrayList<AccountItem>()

data class AccountItem(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val id: Int,
    val name: String,
    val updatedAt: String,
    val userId: Int
)

data class AccountResponse(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val expenseStats: List<ExpenseStat>,
    val id: Int,
    val incomeStats: List<IncomeStat>,
    val name: String,
    val updatedAt: String
)

data class ExpenseStat(
    val amount: String,
    val categoryId: Int,
    val categoryName: String,
    val emoji: String
)

data class StatItem(
    val amount: String,
    val categoryId: Int,
    val categoryName: String,
    val emoji: String
)

data class IncomeStat(
    val amount: String,
    val categoryId: Int,
    val categoryName: String,
    val emoji: String
)

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<History>
)


data class AccountHistory(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<History>
)

data class History(
    val accountId: Int,
    val changeTimestamp: String,
    val changeType: String,
    val createdAt: String,
    val id: Int,
    val newState: NewState,
    val previousState: PreviousState
)

data class NewState(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String
)

data class AccountBrief(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String
)

data class AccountState(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String
)

data class PreviousState(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String
)

data class Transaction(
    val accountId: Int,
    val amount: String,
    val categoryId: Int,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)

data class TransactionRequest(
    val accountId: Int,
    val amount: String,
    val categoryId: Int,
    val comment: String,
    val transactionDate: String
)

class Category : ArrayList<CategoryItem>()

data class CategoryItem(
    val emoji: String,
    val id: Int,
    val isIncome: Boolean,
    val name: String
)

data class TransactionResponse(
    val account: AccountItem,
    val amount: String,
    val category: CategoryItem,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)

