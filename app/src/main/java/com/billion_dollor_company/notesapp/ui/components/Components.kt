package com.billion_dollor_company.notesapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListHolder(
    modifier: Modifier = Modifier,
    content : @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ){
        content()
    }
}