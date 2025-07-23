package com.cursocompose.notapp.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    onNoteSaved: () -> Unit,
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(noteId) {
        viewModel.onEvent(NoteDetailEvent.LoadNote(noteId))
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNoteSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(NoteDetailEvent.TitleChanged(it)) },
            label = { Text("Título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.content,
            onValueChange = { viewModel.onEvent(NoteDetailEvent.ContentChanged(it)) },
            label = { Text("Contenido") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = Int.MAX_VALUE
        )

        Button(
            onClick = { viewModel.onEvent(NoteDetailEvent.SaveNote) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Guardar")
        }
    }
}