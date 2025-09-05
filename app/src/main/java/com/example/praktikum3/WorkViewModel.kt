package com.example.praktikum3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkViewModel(private val repo: WorkRepository = WorkRepository()): ViewModel() {
    val sessions = MutableStateFlow<List<WorkSession>>(emptyList())
    val currentSession = MutableStateFlow<WorkSession?>(null)

    fun startShift() {
        viewModelScope.launch {
            val session = repo.startShift(location = "Filiale-XY")
            currentSession.value = session
        }
    }

    fun endShift(sessionId: String) {
        viewModelScope.launch {
            repo.endShift(sessionId)
            currentSession.value = null
        }
    }

    fun loadSessions() {
        viewModelScope.launch {
            sessions.value = repo.getAllSessions()
        }
    }
}
