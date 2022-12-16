package com.rpfcoding.noteappkmm.domain.note

import com.rpfcoding.noteappkmm.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime,
    val id: Long? = null,
) {
    companion object {
        private val colors = listOf(
            RedOrangeHex,
            RedPinkHex,
            BabyBlueHex,
            VioletHex,
            LightGreenHex
        )

        fun generateRandomColor() = colors.random()
    }
}
