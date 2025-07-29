package com.cursocompose.notapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursocompose.notapp.domain.model.Note
import com.cursocompose.notapp.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailState())
    val state: StateFlow<NoteDetailState> = _state

    private var currentNoteId: Long? = null

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.LoadNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                    try {
                        val note = noteUseCases.getNote(event.id)
                        if (note != null) {
                            currentNoteId = note.id
                            _state.value = _state.value.copy(
                                title = note.title, content = note.content, isLoading = false
                            )
                        } else {
                            _state.value = _state.value.copy(
                                error = "Nota no encontrada.", isLoading = false
                            )
                        }
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            error = "Error al cargar la nota.", isLoading = false
                        )
                    }
                }
            }

            is NoteDetailEvent.TitleChanged -> {
                _state.value = _state.value.copy(title = event.title)
            }

            is NoteDetailEvent.ContentChanged -> {
                _state.value = _state.value.copy(content = event.content)
            }

            is NoteDetailEvent.SaveNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val note = Note(
                            id = currentNoteId ?: System.currentTimeMillis(),
                            title = _state.value.title,
                            content = _state.value.content
                        )
                        noteUseCases.addNote(note)
                        _state.value = _state.value.copy(isSaved = true)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(error = "Error al guardar la nota.")
                    }
                }
            }
        }
    }
}