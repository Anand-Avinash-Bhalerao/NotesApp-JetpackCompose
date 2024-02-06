@file:OptIn(ExperimentalMaterial3Api::class)

package com.billion_dollor_company.notesapp.ui.screen.notes.readNote

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.ui.screen.components.InputText
import com.billion_dollor_company.notesapp.util.Constants

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
                                viewModel.isFavorite = !viewModel.isFavorite
                            }
                        ) {
                            Icon(
                                imageVector = if (viewModel.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                tint = if (viewModel.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Add to favorite"
                            )
                        }
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
            }

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
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        var categoryInfoList = mutableListOf(
            Constants.NoteCategories.GENERAL,
            Constants.NoteCategories.PERSONAL,
            Constants.NoteCategories.PROJECTS,
            Constants.NoteCategories.WORK,
            Constants.NoteCategories.CREDENTIALS
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            var curCategory = viewModel.category.collectAsState().value

            for (chipLabel in categoryInfoList) {
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    selected = curCategory == chipLabel,
                    onClick = {
                        curCategory = chipLabel
                        viewModel.setCategory(chipLabel)
                    },
                    label = { Text(chipLabel) },
                    leadingIcon = if (curCategory == chipLabel) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Localized Description",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    }
                )
            }
        }


        InputText(
            placeHolder = "Title",
            text = viewModel.title,
            onTextChange = {
                viewModel.title = it
            },
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth(),
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
                .padding(top = 12.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(300.dp))
    }

}