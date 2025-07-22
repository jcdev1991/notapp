package com.cursocompose.notapp.domain.usecase

data class NoteUseCases(
    val getNotes: GetNoteListUseCase,
    val getNote: GetNoteUseCase,
    val addNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase
)