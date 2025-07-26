package com.lsimanenka.financetracker.data.mappers

import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.CategoryDbEntity
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionWithDetails
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountBrief
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.model.Category
import com.lsimanenka.financetracker.data.model.StatItem
import com.lsimanenka.financetracker.data.model.Transaction
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse

fun StatItemDbEntity.toStatItem(): StatItem = StatItem(
    categoryId = this.id.toInt(),
    categoryName = this.categoryName,
    emoji = this.emoji,
    amount = this.amount
)

fun StatItem.toDbEntity(accountId: Long, type: String): StatItemDbEntity = StatItemDbEntity(
    id = this.categoryId.toLong(),
    accountId = accountId,
    categoryName = this.categoryName,
    emoji = this.emoji,
    amount = this.amount,
    type = type
)

fun AccountResponse.toDbEntity(): AccountWithDetails =
    AccountWithDetails(account = AccountDbEntity(
        id = this.id.toLong(),
        userId = this.id.toLong(),
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    ),
        stats = this.incomeStats.map { it ->
            StatItemDbEntity(
                id = it.categoryId.toLong(),
                accountId = this.id.toLong(),
                categoryName = it.categoryName,
                emoji = it.emoji,
                amount = it.amount,
                type = "INCOME"
            )
        } + this.expenseStats.map { it ->
            StatItemDbEntity(
                id = it.categoryId.toLong(),
                accountId = this.id.toLong(),
                categoryName = it.categoryName,
                emoji = it.emoji,
                amount = it.amount,
                type = "EXPENSES"
            )
        }
    )

fun AccountWithDetails.toAccountDbEntity(): AccountDbEntity =
    AccountDbEntity(
        id = this.account.id,
        userId = this.account.userId,
        name = this.account.name,
        balance = this.account.balance,
        currency = this.account.currency,
        createdAt = this.account.createdAt,
        updatedAt = this.account.updatedAt
    )

fun Account.toDbEntity(): AccountDbEntity =
    AccountDbEntity(
        id = this.id.toLong(),
        userId = this.id.toLong(),
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )


fun AccountDbEntity.toAccount(): Account =
    Account(
        id = this.id!!.toInt(),
        userId = this.userId.toInt(),
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

fun AccountDbEntity.toUpdateRequest(): AccountUpdateRequest = AccountUpdateRequest(
    name = this.name,
    balance = this.balance,
    currency = this.currency
)


fun AccountWithDetails.toAccountResponse(): AccountResponse {
    val incomeList = stats
        .filter { it.type == "INCOME" }
        .map { it.toStatItem() }

    val expenseList = stats
        .filter { it.type == "EXPENSES" }
        .map { it.toStatItem() }

    return AccountResponse(
        id = account.id!!.toInt(),
        name = account.name,
        balance = account.balance,
        currency = account.currency,
        incomeStats = incomeList,
        expenseStats = expenseList,
        createdAt = account.createdAt,
        updatedAt = account.updatedAt
    )
}

fun Category.toDbEntity(): CategoryDbEntity {
    return CategoryDbEntity(
        id = this.id.toLong(),
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )
}

fun CategoryDbEntity.toCategory(): Category {
    return Category(
        id = this.id.toInt(),
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )
}

fun TransactionResponse.toDbEntity(): TransactionDbEntity = TransactionDbEntity(
    id = this.id.toLong(),
    accountId = this.account.id.toLong(),
    amount = this.amount,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    categoryId = this.category.id.toLong(),
    transactionDate = this.transactionDate,
    comment = this.comment,
)

fun Transaction.toDbEntity(): TransactionDbEntity = TransactionDbEntity(
    id = this.id.toLong(),
    accountId = this.accountId.toLong(),
    amount = this.amount,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    categoryId = this.categoryId.toLong(),
    transactionDate = this.transactionDate,
    comment = this.comment,
)

fun TransactionWithDetails.toTransaction(): Transaction = Transaction(
    id = this.transaction.id.toInt(),
    accountId = this.account.id.toInt(),
    amount = this.transaction.amount,
    createdAt = this.transaction.createdAt,
    updatedAt = this.transaction.updatedAt,
    categoryId = this.category.id.toInt(),
    transactionDate = this.transaction.transactionDate,
    comment = this.transaction.comment,
)

/*fun TransactionRequest.toDbEntity(): TransactionDbEntity = TransactionDbEntity(
    id        = this.,
    accountId = this.accountId.toLong(),
    amount    = this.amount,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)*/

fun TransactionWithDetails.toResponse(): TransactionResponse = TransactionResponse(
    id = this.transaction.id.toInt(),
    account = AccountBrief(
        id = this.account.userId.toInt(),
        name = this.account.name,
        balance = this.account.balance,
        currency = this.account.currency
    ),
    category = Category(
        id = this.category.id.toInt(),
        name = this.category.name,
        emoji = this.category.emoji,
        isIncome = this.category.isIncome
    ),
    amount = this.transaction.amount,
    createdAt = this.transaction.createdAt,
    updatedAt = this.transaction.updatedAt,
    transactionDate = this.transaction.transactionDate,
    comment = this.transaction.comment
)

fun TransactionResponse.toTransactionWithDetails(): TransactionWithDetails = TransactionWithDetails(
    transaction = TransactionDbEntity(
        id = this.id.toLong(),
        accountId = this.account.id.toLong(),
        categoryId = this.category.id.toLong(),
        amount = this.amount,
        transactionDate = this.transactionDate,
        comment = this.comment,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    ),
    account = AccountDbEntity(
        id = this.account.id.toLong(),
        userId = this.account.id.toLong(),
        name = this.account.name,
        balance = this.account.balance,
        currency = this.account.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    ),
    category = CategoryDbEntity(
        id = this.category.id.toLong(),
        name = this.category.name,
        emoji = this.category.emoji,
        isIncome = this.category.isIncome
    )
)

fun TransactionWithDetails.toRequest() : TransactionRequest = TransactionRequest(
    accountId = this.account.id.toInt(),
    categoryId = this.category.id.toInt(),
    amount = this.transaction.amount,
    transactionDate = this.transaction.transactionDate,
    comment = this.transaction.comment
)