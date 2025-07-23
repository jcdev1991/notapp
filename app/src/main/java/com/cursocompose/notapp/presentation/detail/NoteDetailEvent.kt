package com.cursocompose.notapp.presentation.detail

sealed class NoteDetailEvent {
    data class LoadNote(val id: Long) : NoteDetailEvent()
    data class TitleChanged(val title: String) : NoteDetailEvent()
    data class ContentChanged(val content: String) : NoteDetailEvent()
    object SaveNote : NoteDetailEvent()
}