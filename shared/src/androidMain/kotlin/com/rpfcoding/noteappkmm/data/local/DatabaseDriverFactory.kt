package com.rpfcoding.noteappkmm.data.local

import android.content.Context
import com.rpfcoding.noteappkmm.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val ctx: Context
) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            NoteDatabase.Schema, ctx, "note.db"
        )
    }
}