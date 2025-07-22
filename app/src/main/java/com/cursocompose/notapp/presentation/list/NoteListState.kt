package com.cursocompose.notapp.presentation.list

import com.cursocompose.notapp.core.util.Resource
import com.cursocompose.notapp.domain.model.Note

data class NoteListState(
    val state: Resource<List<Note>> = Resource.Loading()
)