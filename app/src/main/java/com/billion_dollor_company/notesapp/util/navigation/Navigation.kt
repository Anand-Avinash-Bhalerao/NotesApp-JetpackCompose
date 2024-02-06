package com.billion_dollor_company.notesapp.util.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.billion_dollor_company.notesapp.ui.screen.notes.NotesScreen
import com.billion_dollor_company.notesapp.ui.screen.notes.readNote.EditScreen
import com.billion_dollor_company.notesapp.ui.screen.tasks.TaskScreen
import com.billion_dollor_company.notesapp.ui.screen.checklist.ChecklistScreen
import com.billion_dollor_company.notesapp.util.Constants

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = Screens.HomeScreen.name
//    ) {
//        composable(
//            route = Screens.HomeScreen.name
//        ) {
//            HomeScreen(
//                onNoteClicked = { uuid ->
//                    Log.d(Constants.DTAG, "uuid in navigation is:{$uuid}")
//                    navController.navigate("${Screens.EditScreen.name}?uuid=${uuid}")
//                },
//                addNote = {
//                    navController.navigate(Screens.EditScreen.name)
//                }
//            )
//        }
//        composable(
//            route = "${Screens.EditScreen.name}?uuid={uuid}",
//            arguments = listOf(
//                navArgument(
//                    name = "uuid"
//                ) {
//                    type = NavType.StringType
//                    defaultValue = ""
//                }
//            )
//
//        ) {
//            EditScreen(
//                onBackPressed = {
//                    navController.navigateUp()
//                }
//            )
//        }
//    }
//}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Tasks.route
    ) {
        composable(route = BottomBarScreen.Tasks.route) {
            TaskScreen()
        }
        composable(route = BottomBarScreen.Notes.route) {
            NotesScreen(
                addNote = {
                    navController.navigate("${Graph.NOTE_DETAILS}")
                },
                onNoteClicked = { uuid ->
                    navController.navigate("${Graph.NOTE_DETAILS}?uuid=${uuid}")
                }
            )
        }
        composable(route = BottomBarScreen.Checklist.route) {
            ChecklistScreen()
        }

        editNoteNavGraph(navController = navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.editNoteNavGraph(
    navController: NavHostController
) {
    navigation(
        route = "${Graph.NOTE_DETAILS}?uuid={uuid}",
        startDestination = "${EditNoteScreen.Edit.route}?uuid={uuid}"
    ) {
        composable(
            route = "${EditNoteScreen.Edit.route}?uuid={uuid}",
            arguments = listOf(
                navArgument(
                    name = "uuid"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            EditScreen(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}

object Graph {
    const val HOME = "home_graph"
    const val NOTE_DETAILS = "note_details_graph"
}

sealed class EditNoteScreen(val route: String) {
    object Edit : EditNoteScreen(route = "editNote")
}