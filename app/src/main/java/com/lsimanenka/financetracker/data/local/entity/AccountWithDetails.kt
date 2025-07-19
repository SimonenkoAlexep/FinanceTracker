package com.lsimanenka.financetracker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithDetails(
    @Embedded val account: AccountDbEntity,

    @Relation(
        parentColumn  = "id",
        entityColumn  = "account_id",
        entity        = StatItemDbEntity::class
    )
    val stats: List<StatItemDbEntity>
)
