package com.cursocompose.notapp.presentation.detail

data class NoteDetailState(
    val title: String = "",
    val content: String = "",
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)