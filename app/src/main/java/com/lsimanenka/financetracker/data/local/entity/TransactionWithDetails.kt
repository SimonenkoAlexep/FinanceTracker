package com.lsimanenka.financetracker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.lsimanenka.financetracker.data.model.AccountBrief
import com.lsimanenka.financetracker.data.model.Category
import com.lsimanenka.financetracker.data.model.TransactionResponse

data class TransactionWithDetails(
    @Embedded val transaction: TransactionDbEntity,

    @Relation(
        parentColumn  = "account_id",
        entityColumn  = "id",
        entity        = AccountDbEntity::class
    )
    val account: AccountDbEntity,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id",
        entity       = CategoryDbEntity::class
    )
    val category: CategoryDbEntity
)

fun TransactionWithDetails.toDomain(): TransactionResponse {
    return TransactionResponse(
        id             = transaction.id.toInt(),
        account        = AccountBrief(
            id       = account.id!!.toInt(),
            name     = account.name,
            balance  = account.balance,
            currency = account.currency
        ),
        category       = Category(
            id = category.id.toInt(),
            name = category.name,
            emoji = category.emoji,
            isIncome = category.isIncome
        ),
        amount         = transaction.amount,
        transactionDate= transaction.transactionDate,
        comment        = transaction.comment,
        createdAt      = transaction.createdAt,
        updatedAt      = transaction.updatedAt
    )
}