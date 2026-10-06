package com.example.manimanimani.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.TagToReason
import kotlinx.coroutines.flow.Flow

@Dao
interface TagToReasonDAO {
    @Query("SELECT * FROM TagToReason WHERE 1=1")
    fun getTagToReasons(): Flow<List<TagToReason>>

    @Insert
    suspend fun insertTagToReason(ttr: TagToReason)

    @Delete
    suspend fun deleteTagToReason(ttr: TagToReason)

    @Query("""
        DELETE FROM TagToReason
        WHERE reason_id = :reasonId
        AND tag_id = :tagId""")
    suspend fun deleteTagToReason(
        reasonId: Int,
        tagId: Int
    )

    @Query("SELECT * FROM TagToReason WHERE reason_id = :id")
    fun getTTRByReasonId(id: Int): Flow<List<TagToReason>>

    @Query("DELETE FROM TagToReason WHERE reason_id = :id")
    suspend fun deleteTTRsByReasonId(id: Int)

    @Query("DELETE FROM TagToReason WHERE tag_id = :id")
    suspend fun deleteTTRsByTagId(id: Int)

    @Query("""
        SELECT ReasonTag.*
        FROM ReasonTag
        INNER JOIN TagToReason
        ON ReasonTag.id = TagToReason.tag_id
        WHERE TagToReason.reason_id = :reasonId
        """)
    fun getTagsForReason(reasonId: Int): Flow<List<ReasonTag>>
}