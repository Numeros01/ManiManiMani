package com.example.manimanimani.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.manimanimani.data.dao.ReceiptDAO
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.data.classes.TagToReason
import com.example.manimanimani.data.dao.ReasonDAO
import com.example.manimanimani.data.dao.TagDAO
import com.example.manimanimani.data.dao.TagToReasonDAO

@Database(
    entities = [
        Receipt::class,
        Reason::class,
        ReasonTag::class,
        TagToReason::class
    ],
    version = 7,
    exportSchema = false
)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDAO
    abstract fun reasonDao(): ReasonDAO
    abstract fun tagDao(): TagDAO
    abstract fun tagToReasonDao(): TagToReasonDAO
}