package com.billion_dollor_company.notesapp.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.billion_dollor_company.notesapp.R
import kotlinx.coroutines.delay

@Composable
fun EmptyLogo(
    displayedString: String = "Nothing found!"
) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LaunchedEffect(Unit) {
            delay(500)
            isLoading = false
        }
        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                val catEmptyComposition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(
                        R.raw.cat_for_empty_scrren
                    )
                )
                LottieAnimation(
                    composition = catEmptyComposition,

                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(300.dp)
                )

                Text(
                    text = displayedString,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
