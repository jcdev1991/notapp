package com.cursocompose.notapp.data.local

import com.cursocompose.notapp.domain.model.Note

fun Note.toEntity(): NoteEntity = NoteEntity(id = id, title = title, content = content)

fun NoteEntity.toDomain(): Note = Note(id = id, title = title, content = content)