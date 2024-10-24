package com.cenkeraydin.words.ui.anim

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.cenkeraydin.words.R

@Composable
fun LottieAnimationView() {
    // Load the animation from raw resources
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottieaddanim))

    // Animation state control (play, pause, loop)
    val animationState = rememberLottieAnimatable()

    // LaunchedEffect to start animation
    LaunchedEffect(composition) {
        composition?.let {
            animationState.animate(
                composition = it,
                iterations = LottieConstants.IterateForever // Loop the animation
            )
        }
    }

    // LottieAnimation composable
    LottieAnimation(
        composition = composition,
        progress = animationState.progress,
        modifier = Modifier
            .size(200.dp) // Adjust size as needed
            .padding(16.dp)
    )
}

@Preview
@Composable
fun PreviewLottieAnimation() {
    LottieAnimationView()
}
