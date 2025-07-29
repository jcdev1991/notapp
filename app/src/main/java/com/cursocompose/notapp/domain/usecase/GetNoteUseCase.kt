package com.cursocompose.notapp.domain.usecase

import com.cursocompose.notapp.domain.model.Note
import com.cursocompose.notapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long): Note? {
        return repository.getNoteById(id)
    }
}