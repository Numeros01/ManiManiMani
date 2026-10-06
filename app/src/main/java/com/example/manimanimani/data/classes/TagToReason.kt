package com.example.manimanimani.data.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = [
        "reason_id",
        "tag_id"
    ]
)
data class TagToReason (
    val reason_id: Int,
    val tag_id: Int
)