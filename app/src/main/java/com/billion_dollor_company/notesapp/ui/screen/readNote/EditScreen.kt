package com.billion_dollor_company.notesapp.ui.screen.readNote

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.ui.components.InputText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScreen(
    onBackPressed: () -> Unit
) {
    val viewModel: EditScreenViewModel = hiltViewModel()
    OuterStructure(
        viewModel = viewModel,
        onBackPressed = onBackPressed
    ) {
        MainContent(viewModel)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OuterStructure(
    viewModel: EditScreenViewModel,
    onBackPressed: () -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = viewModel.pageTitle
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    modifier = Modifier
                        .shadow(elevation = 5.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    actions = {
                        IconButton(
                            onClick = {
                                if (viewModel.title.isNotEmpty() && viewModel.description.isNotEmpty()) {
                                    viewModel.addOrUpdateNote()
                                    onBackPressed()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Title and Description cannot be empty!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Save the Note"
                            )
                        }
                    }
                )
            },

            ) {
            Surface(
                modifier = Modifier
                    .padding(it)
            ) {
                content()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainContent(
    viewModel: EditScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputText(
            placeHolder = "Title",
            text = viewModel.title,
            onTextChange = {
                viewModel.title = it
            },
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth(),
            maxLine = 1,
            style = MaterialTheme.typography.displaySmall
        )
        InputText(
            placeHolder = "Add a note",
            text = viewModel.description,
            onTextChange = {
                viewModel.description = it
            },
            imeAction = ImeAction.Default,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }

}