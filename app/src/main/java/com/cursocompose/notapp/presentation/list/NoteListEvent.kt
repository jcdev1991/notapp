package com.cursocompose.notapp.presentation.list

import com.cursocompose.notapp.domain.model.Note

sealed class NoteListEvent {
    object LoadNotes : NoteListEvent()
    data class AddNote(val note: Note) : NoteListEvent()
    data class DeleteNote(val note: Note) : NoteListEvent()
    object RestoreNote : NoteListEvent()
}