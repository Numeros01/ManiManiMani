package com.example.manimanimani.data.repositories

import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.TagToReason
import com.example.manimanimani.data.dao.TagToReasonDAO
import kotlinx.coroutines.flow.Flow

class TagToReasonRepository(
    private val ttrDao: TagToReasonDAO
) {
    val tagToReasons: Flow<List<TagToReason>> = ttrDao.getTagToReasons()

    suspend fun addTagToReason(ttr: TagToReason) {
        ttrDao.insertTagToReason(ttr)
    }

    suspend fun deleteTagToReason(ttr: TagToReason) {
        ttrDao.deleteTagToReason(ttr)
    }
    suspend fun deleteTagToReason(reasonId: Int, tagId: Int) {
        ttrDao.deleteTagToReason(reasonId, tagId)
    }

    fun getTTRByReasonId(id: Int) : Flow<List<TagToReason>> {
        return ttrDao.getTTRByReasonId(id)
    }

    fun getTagsForReason(reasonId: Int): Flow<List<ReasonTag>> {
        return ttrDao.getTagsForReason(reasonId)
    }
}