package com.billion_dollor_company.notesapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.billion_dollor_company.notesapp.model.NoteInfo
import com.billion_dollor_company.notesapp.util.converters.UUIDConverter
import com.billion_dollor_company.notesapp.util.formatDate

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesInfoCard(
    noteInfo: NoteInfo = NoteInfo(title = "Anand", description = "My name is Anand"),
    onNoteClicked: (String) -> Unit,
    onLongClicked: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(12.dp)))
            .combinedClickable(
                onClick = {
                    onNoteClicked(UUIDConverter().fromUUID(noteInfo.uid))
                },
                onLongClick = {
                    onLongClicked()
                }
            )
    ) {
        Card(
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = noteInfo.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = noteInfo.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = formatDate(noteInfo.entryDate),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Thin
                )
            }
        }
    }
}




