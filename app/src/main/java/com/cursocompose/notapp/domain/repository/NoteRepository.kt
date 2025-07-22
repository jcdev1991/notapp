package com.cursocompose.notapp.domain.repository

import com.cursocompose.notapp.domain.model.Note

interface NoteRepository {
    suspend fun getNotes(): List<Note>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}