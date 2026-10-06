package com.example.manimanimani.ui.dialogManagers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DialogManager {
    private var currentDialog by mutableStateOf(DialogState.NONE)

    fun openDialog(dialog: DialogState) {
        currentDialog = dialog
    }

    fun closeDialog() {
        currentDialog = DialogState.NONE
    }

    fun isOpen(dialog: DialogState) : Boolean {
        return currentDialog == dialog
    }
}