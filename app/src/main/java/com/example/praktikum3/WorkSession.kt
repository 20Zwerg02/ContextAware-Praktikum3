package com.example.praktikum3

data class WorkSession(
    val userId: String = "",
    val startTime: Long = 0L,
    val endTime: Long? = null,
    val pauses: List<Pause> = emptyList(),
    val locationAtStart: String = "",
    val wasOnTime: Boolean = true
)

data class Pause(
    val start: Long = 0L,
    val end: Long = 0L
)
