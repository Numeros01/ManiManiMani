package com.example.manimanimani.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.manimanimani.data.classes.Reason
import kotlinx.coroutines.flow.Flow

@Dao
interface ReasonDAO {

    @Query("SELECT * FROM Reason WHERE 1=1")
    fun getReasons(): Flow<List<Reason>>

    @Query("SELECT * FROM Reason WHERE id = :id LIMIT 1")
    suspend fun getReasonById(id: Int): Reason

    @Insert
    suspend fun insertReason(reason: Reason)

    @Delete
    suspend fun deleteReason(reason: Reason)

    @Update
    suspend fun updateReason(reason: Reason)
}