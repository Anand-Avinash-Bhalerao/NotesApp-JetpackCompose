package com.billion_dollor_company.notesapp.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddTask
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.billion_dollor_company.notesapp.model.TasksInfo
import com.billion_dollor_company.notesapp.ui.components.OutlinedInputText
import com.billion_dollor_company.notesapp.ui.components.TextCheckBox
import com.billion_dollor_company.notesapp.util.Constants
import java.time.Instant
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onNoteClicked: (String) -> Unit,
    addNote: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    Structure(
        viewModel = viewModel,
        addNote = addNote
    ) {
        MainContent(
            viewModel = viewModel,
            onNoteClicked = onNoteClicked
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Structure(
    viewModel: HomeViewModel,
    addNote: () -> Unit,
    content: @Composable () -> Unit
) {
    var openBottom by remember {
        mutableStateOf(false)
    }
    if (openBottom) {
        AddTask(
            onDismiss = {
                openBottom = false
            },
            onAccept = { task ->
                viewModel.addTask(task)
                openBottom = false
            }
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Notes",
                            modifier = Modifier
                                .padding(start = 16.dp),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    },
                    modifier = Modifier
                        .shadow(elevation = 5.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (viewModel.selectedTabIndex == Constants.Screens.NOTES_PAGE)
                            addNote()
                        else
                            openBottom = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add a note"
                    )
                }
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


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainContent(
    viewModel: HomeViewModel,
    onNoteClicked: (String) -> Unit
) {
    val pagerState = rememberPagerState {
        viewModel.tabItems.size
    }
    LaunchedEffect(viewModel.selectedTabIndex) {
        pagerState.animateScrollToPage(viewModel.selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress)
            viewModel.selectedTabIndex = pagerState.currentPage
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = viewModel.selectedTabIndex
        ) {
            viewModel.tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == viewModel.selectedTabIndex,
                    onClick = {
                        viewModel.selectedTabIndex = index
                    },
                    text = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == viewModel.selectedTabIndex)
                                item.selectedIcon
                            else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageNo ->
            when (pageNo) {
                Constants.Screens.TASK_PAGE -> TaskSection(
                    viewModel = viewModel
                )
                Constants.Screens.NOTES_PAGE -> NoteSection(
                    viewModel = viewModel,
                    onNoteClicked = onNoteClicked
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    onDismiss: () -> Unit,
    onAccept: (TasksInfo) -> Unit
) {
    Column {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            dragHandle = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Add new task",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        ) {
            var currentText by remember {
                mutableStateOf("")
            }
            var isChecked by remember {
                mutableStateOf(false)
            }
            Column {
                OutlinedInputText(
                    text = currentText,
                    placeHolder = "Enter here",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 4.dp)
                        .fillMaxWidth()
                ) { newText ->
                    currentText = newText
                }
                TextCheckBox(
                    title = "Set this task for tomorrow",
                    isChecked = isChecked,
                    shouldStrikeThrough = false
                ) {
                    isChecked = !isChecked
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilledTonalButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = "Discard")
                    }
                    Button(
                        onClick = {
                            onAccept(
                                TasksInfo(
                                    title = currentText
                                )
                            )
                        },
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddTask,
                            contentDescription = "Add task button icon"
                        )
                        Spacer(
                            modifier = Modifier.size(ButtonDefaults.IconSpacing)
                        )
                        Text(text = "Add")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}