package com.cursocompose.notapp.presentation.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursocompose.notapp.domain.model.Note
import com.cursocompose.notapp.domain.usecase.NoteUseCases
import com.cursocompose.notapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    var state = mutableStateOf(NoteListState())
        private set

    private var recentlyDeletedNote: Note? = null
    private var loadJob: Job? = null

    init {
        onEvent(NoteListEvent.LoadNotes)
    }

    fun onEvent(event: NoteListEvent) {

        when (event) {

            is NoteListEvent.LoadNotes -> {
                loadJob?.cancel()
                state.value = state.value.copy(state = Resource.Loading())
                loadJob = viewModelScope.launch {
                    try {
                        val notes = noteUseCases.getNotes()
                        state.value = state.value.copy(state = Resource.Success(notes))
                    } catch (e: Exception) {
                        state.value =
                            state.value.copy(state = Resource.Error("Error al cargar notas"))
                    }
                }
            }

            is NoteListEvent.AddNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(event.note)
                    onEvent(NoteListEvent.LoadNotes)
                }
            }

            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                    onEvent(NoteListEvent.LoadNotes)
                }
            }

            is NoteListEvent.RestoreNote -> {
                recentlyDeletedNote?.let { note ->
                    viewModelScope.launch {
                        noteUseCases.addNote(note)
                        recentlyDeletedNote = null
                        onEvent(NoteListEvent.LoadNotes)
                    }
                }
            }
        }
    }
}