package com.rpfcoding.noteappkmm.domain.model

data class Municipality(
    val name: String,
    val barangayList: List<Barangay>
)
