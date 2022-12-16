package com.rpfcoding.noteappkmm.domain.note

import com.rpfcoding.noteappkmm.domain.time.DateTimeUtil

class SearchNotes {

    fun execute(notes: List<Note>, query: String): List<Note> {
        if(query.isBlank()) {
            return notes
        }

        return notes.filter {
            it.title.trim().lowercase().contains(query, true) ||
                    it.content.trim().lowercase().contains(query, true)
        }.sortedByDescending {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}