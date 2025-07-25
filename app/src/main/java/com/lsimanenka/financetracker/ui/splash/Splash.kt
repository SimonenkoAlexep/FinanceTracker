package com.lsimanenka.financetracker.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.MyColors

@Composable
fun LottieSplashScreen(
    onFinished: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        restartOnPlay = false,
        speed = 1f
    )

    LaunchedEffect(progress) {
        if (progress == 1f) {
            onFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColors.primary),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(200.dp)
        )
    }
}
