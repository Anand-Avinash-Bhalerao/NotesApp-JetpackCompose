package com.billion_dollor_company.notesapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.billion_dollor_company.notesapp.ui.screen.addNote.AddScreen
import com.billion_dollor_company.notesapp.ui.screen.home.HomeScreen
import com.billion_dollor_company.notesapp.ui.screen.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name
    ) {
        composable(
            Screens.HomeScreen.name
        ) {
            HomeScreen(
                addNote = {
                    navController.navigate(Screens.AddScreen.name)
                }
            )
        }
        composable(
            Screens.AddScreen.name
        ) {
            AddScreen(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}