package com.billion_dollor_company.notesapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.billion_dollor_company.notesapp.ui.screen.temp.RootScreen
import com.billion_dollor_company.notesapp.ui.theme.NotesAppTheme
import com.billion_dollor_company.notesapp.util.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NotesAppTheme {
                RootScreen()
            }
        }
    }
}