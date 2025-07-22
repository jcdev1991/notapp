package com.cursocompose.notapp.data.local

import com.cursocompose.notapp.domain.model.Note

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp
    )
}