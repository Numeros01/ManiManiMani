package com.example.manimanimani.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.manimanimani.data.classes.Receipt
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDAO {
    @Query("SELECT * FROM Receipt WHERE 1=1")
    fun getReceipts(): Flow<List<Receipt>>

    @Query("DELETE FROM Receipt WHERE isHidden = 0")
    suspend fun deleteAllReceipts()

    @Insert
    suspend fun insertReceipt(receipt: Receipt)

    @Delete
    suspend fun deleteReceipt(receipt: Receipt)

    @Update
    suspend fun updateReceipt(receipt: Receipt)
}