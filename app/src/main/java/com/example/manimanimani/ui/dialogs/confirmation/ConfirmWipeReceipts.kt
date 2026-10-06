package com.example.manimanimani.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.ui.theme.*
import com.example.manimanimani.viewmodel.ReceiptViewModel

@Composable
fun ConfirmWipeReceipts(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "Yes",
                    color = SoftWhite
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "Cancel",
                    color = SoftWhite
                )
            }
        },
        title = {
            Text(
                "Are you sure?",
                color = MutedGray
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Are you sure to delete all receipts?",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite,
                    lineHeight = 40.sp
                )
            }
        },
        containerColor = DeepCharcoal
    )
}