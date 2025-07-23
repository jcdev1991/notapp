package com.cursocompose.notapp.presentation.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cursocompose.notapp.domain.model.Note

@Composable
fun NoteListItem(note: Note, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.id.toString(), style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar nota")
            }
        }
    }
}