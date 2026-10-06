package com.example.manimanimani.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.manimanimani.data.repositories.ReasonRepository
import com.example.manimanimani.data.repositories.ReasonTagRepository
import com.example.manimanimani.data.repositories.ReceiptRepository
import com.example.manimanimani.data.repositories.TagToReasonRepository

class ReceiptViewModelFactory(
    private val receiptRepo: ReceiptRepository,
    private val reasonRepo: ReasonRepository,
    private val tagRepo: ReasonTagRepository,
    private val tagToReasonRepo: TagToReasonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ReceiptViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return ReceiptViewModel(
                receiptRepo = receiptRepo,
                reasonRepo = reasonRepo,
                tagRepo = tagRepo,
                ttrRepo = tagToReasonRepo
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}