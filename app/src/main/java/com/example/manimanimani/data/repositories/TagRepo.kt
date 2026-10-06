package com.example.manimanimani.data.repositories

import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.dao.TagDAO
import com.example.manimanimani.data.dao.TagToReasonDAO
import kotlinx.coroutines.flow.Flow

class ReasonTagRepository(
    private val tagDao: TagDAO,
    private val ttrDao: TagToReasonDAO
) {
    val tags: Flow<List<ReasonTag>> = tagDao.getTags()

    suspend fun addTag(tag: ReasonTag) {
        tagDao.insertTag(tag)
    }

    suspend fun deleteTag(tag: ReasonTag) {
        tagDao.deleteTag(tag)
        ttrDao.deleteTTRsByTagId(tag.id)
    }

    suspend fun updateTag(tag: ReasonTag) {
        tagDao.updateTag(tag)
    }

    suspend fun getTagById(id: Int) : ReasonTag {
        return tagDao.getTagById(id)
    }
}