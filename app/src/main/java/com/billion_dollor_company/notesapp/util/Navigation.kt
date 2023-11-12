package com.billion_dollor_company.notesapp.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.billion_dollor_company.notesapp.ui.screen.home.HomeScreen
import com.billion_dollor_company.notesapp.ui.screen.readNote.EditScreen

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
                    Log.d(Constants.DTAG, "uuid in navigation is:{$uuid}")
                    navController.navigate("${Screens.EditScreen.name}?uuid=${uuid}")
                },
                addNote = {
                    navController.navigate(Screens.EditScreen.name)
                }
            )
        }
        composable(
            route = "${Screens.EditScreen.name}?uuid={uuid}",
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