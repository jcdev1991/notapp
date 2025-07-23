package com.cursocompose.notapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cursocompose.notapp.presentation.detail.NoteDetailScreen
import com.cursocompose.notapp.presentation.list.NoteListScreen

@Composable
fun NoteNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.NOTE_LIST,
        modifier = modifier
    ) {
        composable(route = NavigationRoute.NOTE_LIST) {
            NoteListScreen(navController)
        }

        composable(
            route = NavigationRoute.NOTE_DETAIL_WITH_ID, arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                    defaultValue = -1L
                })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            NoteDetailScreen(
                noteId = noteId, onNoteSaved = { navController.popBackStack() })
        }
    }
}
