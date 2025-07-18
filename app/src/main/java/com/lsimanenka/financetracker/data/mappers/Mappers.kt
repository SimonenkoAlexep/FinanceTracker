package com.lsimanenka.financetracker.data.mappers

import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.model.StatItem

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
        createdAt = this.account.createdAt  ,
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