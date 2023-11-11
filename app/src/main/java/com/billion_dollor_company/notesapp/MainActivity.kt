package com.billion_dollor_company.notesapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.billion_dollor_company.notesapp.ui.screen.home.HomeScreen
import com.billion_dollor_company.notesapp.ui.screen.home.HomeViewModel
import com.billion_dollor_company.notesapp.ui.theme.NotesAppTheme
import com.billion_dollor_company.notesapp.util.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Navigation()
            }
        }
    }
}