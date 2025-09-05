package com.example.praktikum3

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class WorkRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun startShift(location: String): WorkSession {
        val uid = auth.currentUser?.uid ?: "unknown"
        val session = WorkSession(
            userId = uid,
            startTime = System.currentTimeMillis(),
            locationAtStart = location,
            wasOnTime = true // TODO: Vergleich mit Laden-Koordinaten
        )
        db.collection("sessions").add(session).await()
        return session
    }

    suspend fun endShift(sessionId: String) {
        val endTime = System.currentTimeMillis()
        db.collection("sessions").document(sessionId)
            .update("endTime", endTime).await()
    }

    suspend fun getAllSessions(): List<WorkSession> {
        val snapshot = db.collection("sessions").get().await()
        return snapshot.toObjects(WorkSession::class.java)
    }
}
