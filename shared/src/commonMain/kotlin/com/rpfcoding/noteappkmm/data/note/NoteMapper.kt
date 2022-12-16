package com.rpfcoding.noteappkmm.data.note

import com.rpfcoding.noteappkmm.domain.note.Note
import com.rpfcoding.noteappkmm.domain.time.DateTimeUtil
import database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNote(): Note {
    return Note(
        title = title,
        content = content,
        colorHex = colorHex,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        id = id
    )
}