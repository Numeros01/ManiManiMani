package com.example.manimanimani.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.manimanimani.data.classes.ReasonTag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDAO {
    @Query("SELECT * FROM ReasonTag WHERE 1=1")
    fun getTags(): Flow<List<ReasonTag>>

    @Insert
    suspend fun insertTag(reasonTag: ReasonTag)

    @Delete
    suspend fun deleteTag(reasonTag: ReasonTag)

    @Update
    suspend fun updateTag(reasonTag: ReasonTag)

    @Query("SELECT * FROM ReasonTag WHERE id = :id LIMIT 1")
    suspend fun getTagById(id: Int) : ReasonTag
}