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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS favoritos (
                id TEXT NOT NULL,
                titulo TEXT NOT NULL,
                descripcion TEXT NOT NULL,
                division TEXT NOT NULL,
                categoria TEXT NOT NULL,
                dificultad TEXT NOT NULL,
                productosMartini TEXT NOT NULL,
                rindePorciones INTEGER NOT NULL,
                ingredientes TEXT NOT NULL,
                pasos TEXT NOT NULL,
                imagenResId INTEGER NOT NULL,
                esVegano INTEGER NOT NULL,
                PRIMARY KEY(id)
            )
            """.trimIndent()
        )
    }
}
