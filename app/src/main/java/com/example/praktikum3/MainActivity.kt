package com.example.praktikum3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum3.ui.theme.Praktikum3Theme
import com.google.firebase.FirebaseApp
import java.util.Date

class MainActivity : ComponentActivity() {

    private lateinit var vm: WorkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        vm = WorkViewModel()

        setContent {
            RoleSelectionScreen(vm)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Praktikum3Theme {
        Greeting("Android")
    }
}

@Composable
fun MitarbeiterScreen(vm: WorkViewModel) {
    val session by vm.currentSession.collectAsState(initial = null)

    Column(Modifier.padding(16.dp)) {
        Text("Mitarbeiter-Ansicht")

        Button(onClick = { vm.startShift() }) {
            Text("Arbeitszeit starten")
        }

        if (session != null) {
            Button(onClick = { vm.endShift(session!!.userId) }) {
                Text("Arbeitszeit beenden")
            }
            Text("Arbeitsbeginn: ${java.util.Date(session!!.startTime)}")
        }
    }
}


@Composable
fun FilialleiterScreen(vm: WorkViewModel) {
    val sessions by vm.sessions.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text("Filialleiter-Ansicht")

        Button(onClick = { vm.loadSessions() }) {
            Text("Daten laden")
        }

        LazyColumn {
            items(sessions) { s ->
                Card(Modifier.padding(8.dp)) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Mitarbeiter: ${s.userId}")
                        Text("Start: ${Date(s.startTime)}")
                        Text("Ende: ${s.endTime?.let { Date(it) } ?: "läuft"}")
                    }
                }
            }
        }
    }
}


@Composable
fun RoleSelectionScreen(vm: WorkViewModel) {
    var role by remember { mutableStateOf<String?>(null) }

    if (role == null) {
        Column(Modifier.padding(16.dp)) {
            Button(onClick = { role = "mitarbeiter" }) { Text("Mitarbeiter") }
            Button(onClick = { role = "filialleiter" }) { Text("Filialleiter") }
        }
    } else {
        if (role == "mitarbeiter") MitarbeiterScreen(vm)
        else FilialleiterScreen(vm)
    }
}
