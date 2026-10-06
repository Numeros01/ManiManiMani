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
fun SumDialog(
    receipts: List<Receipt>,
    vm: ReceiptViewModel,
    onReset: () -> Unit,
    onConfirm: () -> Unit
) {
    var amount by remember { mutableIntStateOf(0) }

    LaunchedEffect(receipts) {
        for(receipt in receipts) {
            if(!receipt.isHidden) {
                amount += receipt.amount
            }
        }
    }

    fun isError() : Boolean {
        return false
    }

    fun errorText() : String {
        return "Something went wrong!"
    }

    AlertDialog(
        onDismissRequest = onConfirm,
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "OK",
                    color = SoftWhite
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onReset,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "Reset all Data",
                    color = SoftWhite
                )
            }
        },
        title = {
            Text(
                if (isError()) errorText() else "Summary of Bills",
                color = MutedGray
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total amount:",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )

                Text(
                    text = amount.toString(),
                    fontSize = 24.sp,
                    color = MMMAccent,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        containerColor = (
            if (isError())
                MMMError
            else
                DeepCharcoal
        )
    )
}