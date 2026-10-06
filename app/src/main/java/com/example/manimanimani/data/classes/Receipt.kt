package com.example.manimanimani.data.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var amount: Int,
    var reason_id: Int? = null,
    var reasonText: String = "",
    var description: String = "",
    var creation_time: Long = 0, // in miliseconds
    var isHidden: Boolean = false
)

val receiptZero = Receipt(
    id = -35342,
    amount = -67825455
)