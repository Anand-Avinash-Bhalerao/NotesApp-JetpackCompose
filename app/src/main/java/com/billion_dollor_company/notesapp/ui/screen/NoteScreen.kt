package com.billion_dollor_company.notesapp.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.ui.components.NoteInputTextField
import com.billion_dollor_company.notesapp.util.formatDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(
    notesList: List<NoteInfo>,
    onAddNote: (NoteInfo) -> Unit = {},
    onDeleteNote: (NoteInfo) -> Unit = {}
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Notes"
                        )
                    },
                    modifier = Modifier
                        .shadow(elevation = 5.dp),
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
                    },
                    shape = CircleShape,

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
                val context = LocalContext.current
                var title by remember {
                    mutableStateOf("")
                }
                var description by remember {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NoteInputTextField(
                        label = "Title",
                        text = title,
                        onTextChange = {
                            title = it
                        },
                        imeAction = ImeAction.Next,
                        modifier = Modifier.fillMaxWidth()
                    )
                    NoteInputTextField(
                        label = "Add a note",
                        text = description,
                        onTextChange = {
                            description = it
                        },
                        imeAction = ImeAction.Done,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )

                    Button(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(top = 24.dp),

                        onClick = {
                            if (title.isNotEmpty() and description.isNotEmpty()) {
                                onAddNote(NoteInfo(title = title, description = description))
                                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                                title = ""
                                description = ""

                            }
                        }
                    ) {
                        Text(text = "Save")
                    }

                    Divider(
                        Modifier.padding(vertical = 12.dp)
                    )

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 4.dp,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        content = {
                            items(notesList) { note ->
                                InfoCard(note) {
                                    onDeleteNote(note)
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoCard(
    noteInfo: NoteInfo = NoteInfo(title = "Anand", description = "My name is Anand"),
    onNoteClicked: (NoteInfo) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(12.dp)))
            .clickable {
                onNoteClicked(noteInfo)
            }
    ) {
        Card(
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = noteInfo.title)
                Text(text = noteInfo.description)
                Text(text = formatDate(noteInfo.entryDate.time))
            }
        }
    }

}