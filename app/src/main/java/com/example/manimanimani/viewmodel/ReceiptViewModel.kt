package com.example.manimanimani.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.repositories.ReceiptRepository
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.data.classes.TagToReason
import com.example.manimanimani.data.classes.reasonZero
import com.example.manimanimani.data.classes.receiptZero
import com.example.manimanimani.data.classes.tagZero
import com.example.manimanimani.data.repositories.ReasonRepository
import com.example.manimanimani.data.repositories.ReasonTagRepository
import com.example.manimanimani.data.repositories.TagToReasonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReceiptViewModel(
    private val receiptRepo: ReceiptRepository,
    private val reasonRepo: ReasonRepository,
    private val tagRepo: ReasonTagRepository,
    private val ttrRepo: TagToReasonRepository
) : ViewModel() {
    val receipts = receiptRepo.receipts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val reasons = reasonRepo.reasons.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val tags = tagRepo.tags.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val tagToReasons = ttrRepo.tagToReasons.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addReceipt(
        amount: Int,
        reason: Reason? = null,
        reasonText: String = "",
        description: String = "",
        creation_time: Long = 0L,
        isHidden: Boolean = false
    ) {
        var time = creation_time
        if(time == 0L)
            time = System.currentTimeMillis()

        viewModelScope.launch {
            receiptRepo.addReceipt(
                Receipt(
                    amount = amount,
                    reason_id = reason?.id,
                    reasonText = reasonText,
                    description = description,
                    creation_time = time,
                    isHidden = isHidden
                )
            )
        }
    }

    fun deleteReceipt(receipt: Receipt) {
        if(receipt == receiptZero)
            return
        viewModelScope.launch {
            receiptRepo.deleteReceipt(receipt)
        }
    }

    fun updateReceipt(receiptCopy: Receipt) {
        viewModelScope.launch {
            receiptRepo.updateReceipt(
                receiptCopy.copy()
            )
        }
    }

    fun hideReceipt(receipt: Receipt, doHide: Boolean) {
        viewModelScope.launch {
            receiptRepo.updateReceipt(
                receipt.copy(isHidden = doHide)
            )
        }
    }

    fun addReason(name: String, description: String = "", amount: Int? = null) {
        addReason(Reason(
            name = name,
            description = description,
            constAmount = amount
        ))
    }
    fun addReason(reason: Reason) {
        viewModelScope.launch {
            reasonRepo.addReason(reason)
        }
    }

    fun deleteReason(reason: Reason) {
        if(reason == reasonZero)
            return
        viewModelScope.launch {
            reasonRepo.deleteReason(reason)
        }
    }

    fun updateReason(reason: Reason) {
        viewModelScope.launch {
            reasonRepo.updateReason(reason)
        }
    }

    fun hideReason(reason: Reason, doHide: Boolean) {
        viewModelScope.launch {
            reasonRepo.updateReason(reason.copy(
                isHidden = doHide
            ))
        }
    }

    fun addTag(text: String) {
        addTag(ReasonTag(text = text))
    }
    fun addTag(tag: ReasonTag) {
        viewModelScope.launch {
            tagRepo.addTag(tag)
        }
    }

    fun deleteTag(tag: ReasonTag) {
        if(tag == tagZero)
            return
        viewModelScope.launch {
            tagRepo.deleteTag(tag)
        }
    }

    fun updateTag(tag: ReasonTag) {
        viewModelScope.launch {
            tagRepo.updateTag(tag)
        }
    }

    fun addTagToReason(reasonId: Int, tagId: Int) {
        viewModelScope.launch {
            ttrRepo.addTagToReason(TagToReason(
                reasonId,
                tagId
            ))
        }
    }

    fun deleteTagToReason(
        reasonId: Int,
        tagId: Int
    ) {
        viewModelScope.launch {
            ttrRepo.deleteTagToReason(
                reasonId,
                tagId
            )
        }
    }

    suspend fun getReasonById(id: Int?): Reason? {
        if(id == null)
            return null
        else
            return reasonRepo.getReasonById(id)
    }

    suspend fun getTagById(id: Int) : ReasonTag {
        return tagRepo.getTagById(id)
    }

    fun getTTRByReasonId(id: Int) : Flow<List<TagToReason>> {
        return ttrRepo.getTTRByReasonId(id)
    }

    fun getTagsForReason(reasonId: Int): Flow<List<ReasonTag>> {
        return ttrRepo.getTagsForReason(reasonId)
    }

    fun deleteAllReceipts() {
        viewModelScope.launch {
            receiptRepo.deleteAllReceipts()
        }
    }

    fun checkReasonExist(reason: Reason) : Boolean {
        val alreadyExists =
            reasons.value.any {
                it.name.trim().lowercase() == reason.name.trim().lowercase()
            }
        return alreadyExists
    }

    fun checkTagExist(tag: ReasonTag) : Boolean {
        val alreadyExists =
            tags.value.any {
                it.text.trim().lowercase() == tag.text.trim().lowercase()
            }
        return alreadyExists
    }
}