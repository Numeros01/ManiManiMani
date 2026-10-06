package com.example.manimanimani.data.classes

import androidx.collection.mutableIntListOf
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reason (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var description: String = "",
    var constAmount: Int? = null
)

val reasonZero = Reason(
    id = -246521,
    name = "Empty Reason"
)