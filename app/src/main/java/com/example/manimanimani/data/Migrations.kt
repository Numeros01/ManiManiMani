package com.example.manimanimani.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

public val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            ALTER TABLE Receipt
            ADD COLUMN creation_time INTEGER
            """.trimIndent()
        )
    }
}

public val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE Receipt_new (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                amount INTEGER NOT NULL,
                reason_id INTEGER,
                reasonText TEXT NOT NULL,
                description TEXT NOT NULL,
                creation_time INTEGER NOT NULL,
                isHidden INTEGER NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            INSERT INTO Receipt_new (
                amount,
                reason_id,
                reasonText,
                description,
                creation_time,
                isHidden
            )
            SELECT
                amount,
                reason_id,
                reasonText,
                description,
                creation_time,
                0
            FROM Receipt
        """.trimIndent())

        db.execSQL("DROP TABLE Receipt")

        db.execSQL("ALTER TABLE Receipt_new RENAME TO Receipt")
    }
}

public val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE Reason_new (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                constAmount INTEGER,
                isHidden INTEGER NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            INSERT INTO Reason_new (
                name,
                description,
                constAmount,
                isHidden
            )
            SELECT
                name,
                description,
                constAmount,
                0
            FROM Reason
        """.trimIndent())

        db.execSQL("DROP TABLE Reason")

        db.execSQL("ALTER TABLE Reason_new RENAME TO Reason")
    }
}