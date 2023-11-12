package com.billion_dollor_company.notesapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.billion_dollor_company.notesapp.ui.screen.addNote.AddScreen
import com.billion_dollor_company.notesapp.ui.screen.home.HomeScreen
import com.billion_dollor_company.notesapp.ui.screen.readNote.ReadScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name
    ) {
        composable(
            route = Screens.HomeScreen.name
        ) {
            HomeScreen(
                onNoteClicked = { uuid ->
                    navController.navigate("${Screens.ReadScreen.name}/${uuid}")
                },
                addNote = {
                    navController.navigate(Screens.AddScreen.name)
                }
            )
        }
        composable(
            route = Screens.AddScreen.name
        ) {
            AddScreen(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "${Screens.ReadScreen.name}/{uuid}",
            arguments = listOf(
                navArgument(
                    name = "uuid"
                ) {
                    type = NavType.StringType
                }
            )

        ) {
            ReadScreen(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}