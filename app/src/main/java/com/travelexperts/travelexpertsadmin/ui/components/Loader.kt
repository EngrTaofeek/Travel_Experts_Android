package com.travelexperts.travelexpertsadmin.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.travelexperts.travelexpertsadmin.R

@Composable
fun CustomLoader(){
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loader)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(200.dp).align(Alignment.Center)
        )
    }

}