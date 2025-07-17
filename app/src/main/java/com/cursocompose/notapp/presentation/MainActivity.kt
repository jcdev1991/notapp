package com.cursocompose.notapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.cursocompose.notapp.presentation.navigation.NoteNavHost
import com.cursocompose.notapp.ui.theme.NotappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotappTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NoteNavHost()
                }
            }
        }
    }
}