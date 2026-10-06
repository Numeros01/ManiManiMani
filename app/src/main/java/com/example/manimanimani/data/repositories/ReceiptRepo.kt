package com.example.manimanimani.data.repositories

import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.data.dao.ReceiptDAO
import kotlinx.coroutines.flow.Flow

class ReceiptRepository(
    private val receiptDao: ReceiptDAO
) {
    val receipts: Flow<List<Receipt>> = receiptDao.getReceipts()
    suspend fun addReceipt(receipt: Receipt) {
        receiptDao.insertReceipt(receipt)
    }

    suspend fun deleteReceipt(receipt: Receipt) {
        receiptDao.deleteReceipt(receipt)
    }

    suspend fun updateReceipt(receipt: Receipt) {
        receiptDao.updateReceipt(receipt)
    }

    suspend fun deleteAllReceipts() {
        receiptDao.deleteAllReceipts()
    }
}