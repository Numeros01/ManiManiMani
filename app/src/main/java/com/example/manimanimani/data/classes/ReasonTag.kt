package com.example.manimanimani.data.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReasonTag(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var text: String
)

val tagZero = ReasonTag(
    id = -5726245,
    text = "Empty Tag"
)