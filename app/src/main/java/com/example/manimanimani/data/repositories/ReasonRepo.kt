package com.example.manimanimani.data.repositories

import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.dao.ReasonDAO
import com.example.manimanimani.data.dao.TagToReasonDAO
import kotlinx.coroutines.flow.Flow

class ReasonRepository(
    private val reasonDao: ReasonDAO,
    private val ttrDao: TagToReasonDAO
) {
    val reasons: Flow<List<Reason>> = reasonDao.getReasons()

    suspend fun addReason(reason: Reason) {
        reasonDao.insertReason(reason)
    }

    suspend fun deleteReason(reason: Reason) {
        reasonDao.deleteReason(reason)
        ttrDao.deleteTTRsByReasonId(reason.id)
    }

    suspend fun updateReason(reason: Reason) {
        reasonDao.updateReason(reason)
    }

    suspend fun getReasonById(id: Int): Reason {
        return reasonDao.getReasonById(id)
    }
}