package com.example.manimanimani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.manimanimani.data.MIGRATION_4_5
import com.example.manimanimani.data.MIGRATION_5_6
import com.example.manimanimani.data.ReceiptDatabase
import com.example.manimanimani.data.repositories.ReasonRepository
import com.example.manimanimani.data.repositories.ReasonTagRepository
import com.example.manimanimani.data.repositories.ReceiptRepository
import com.example.manimanimani.data.repositories.TagToReasonRepository
import com.example.manimanimani.navigation.NavGraph
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.theme.ManiManiManiTheme
import com.example.manimanimani.viewmodel.ReceiptViewModel
import com.example.manimanimani.viewmodel.ReceiptViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            ReceiptDatabase::class.java,
            "receipts.db"
        )
            .addMigrations(
                MIGRATION_4_5,
                MIGRATION_5_6
            )
            .build()

        val receiptRepo = ReceiptRepository(
            db.receiptDao()
        )
        val reasonRepo = ReasonRepository(
            db.reasonDao(),
            db.tagToReasonDao()
        )
        val tagRepo = ReasonTagRepository(
            db.tagDao(),
            db.tagToReasonDao()
        )
        val tagToReasonRepo = TagToReasonRepository(
            db.tagToReasonDao()
        )

        val factory = ReceiptViewModelFactory(
            receiptRepo = receiptRepo,
            reasonRepo = reasonRepo,
            tagRepo = tagRepo,
            tagToReasonRepo = tagToReasonRepo
        )

        val MMMDialogManager = DialogManager()
        val MMMErrorManager = ErrorManager()

        setContent {
            ManiManiManiTheme {
                val MMMViewModel: ReceiptViewModel = viewModel(
                    factory = factory
                )

                NavGraph(
                    MMMViewModel = MMMViewModel,
                    MMMDialogManager = MMMDialogManager,
                    MMMErrorManager = MMMErrorManager
                )
            }
        }
    }
}