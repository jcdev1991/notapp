package com.cursocompose.notapp.domain.usecase

import com.cursocompose.notapp.domain.model.Note
import com.cursocompose.notapp.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }
}