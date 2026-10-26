package com.mainsteps.martiniconecta.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE perfil ADD COLUMN letraGrande INTEGER NOT NULL DEFAULT 0"
        )
    }
}
