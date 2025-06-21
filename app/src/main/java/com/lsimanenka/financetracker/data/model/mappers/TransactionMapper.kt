package com.lsimanenka.financetracker.data.model.mappers

import com.lsimanenka.financetracker.data.model.Transaction
import com.lsimanenka.financetracker.data.model.TransactionResponse

fun TransactionResponse.toDomain(): Transaction =
    Transaction(
        id = id,
        accountId = account.id,
        categoryId = category.id,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
