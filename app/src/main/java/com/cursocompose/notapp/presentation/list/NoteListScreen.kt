package com.cursocompose.notapp.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cursocompose.notapp.core.util.Resource
import com.cursocompose.notapp.presentation.list.components.NoteCreateDialog
import com.cursocompose.notapp.presentation.list.components.NoteListItem
import com.cursocompose.notapp.presentation.navigation.NavigationRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavController, viewModel: NoteListViewModel = hiltViewModel()) {

    val state by viewModel.state
    var isDialogOpen by remember { mutableStateOf(false) }
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(NoteListEvent.LoadNotes)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Notas") }) }, floatingActionButton = {
        FloatingActionButton(onClick = { isDialogOpen = true }) {
            Icon(Icons.Default.Add, contentDescription = "Añadir nota")
        }
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val result = state.state) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is Resource.Error -> {
                    Text(
                        text = result.message ?: "Error desconocido",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(result.data ?: emptyList()) { note ->
                            NoteListItem(note = note, onClick = {
                                navController.navigate(
                                    NavigationRoute.noteDetailWithId(note.id)
                                )
                            }, onDelete = {
                                viewModel.onEvent(NoteListEvent.DeleteNote(note))
                            })
                        }
                    }
                }
            }

            if (isDialogOpen) {
                NoteCreateDialog(onDismiss = { isDialogOpen = false }, onCreateNote = { note ->
                    viewModel.onEvent(NoteListEvent.AddNote(note))
                })
            }
        }
    }
}