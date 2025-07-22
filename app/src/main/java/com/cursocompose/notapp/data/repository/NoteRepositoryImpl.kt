package com.cursocompose.notapp.data.repository

import com.cursocompose.notapp.data.local.NoteDao
import com.cursocompose.notapp.data.local.toDomain
import com.cursocompose.notapp.data.local.toEntity
import com.cursocompose.notapp.domain.model.Note
import com.cursocompose.notapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override suspend fun getNotes(): List<Note> {
        return dao.getAllNotes().map { it.toDomain() }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toDomain()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }
}