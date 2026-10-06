package com.example.manimanimani.ui.dialogs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.manimanimani.ui.components.ErrorState

class ErrorManager {
    private var currentError by mutableStateOf(ErrorState.NONE)

    fun getError() : ErrorState {
        return currentError
    }

    fun showError(error: ErrorState) {
        currentError = error
    }

    fun closeError() {
        currentError = ErrorState.NONE
    }

    fun isError(error: ErrorState) : Boolean {
        return currentError == error
    }

    fun isError(errors: List<ErrorState>) : Boolean {
        for(error in errors) {
            if(currentError == error)
                return true
        }
        return false
    }
}