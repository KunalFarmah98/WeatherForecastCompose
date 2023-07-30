package com.kunalfarmah.apps.weatherforcastcompose.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.kunalfarmah.apps.weatherforcastcompose.R
import com.kunalfarmah.apps.weatherforcastcompose.nav.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen( navController: NavController) {
    val scale = remember {
       Animatable(0f)
    }
    val defaultCity = "delhi"

    // to launch a coroutine right after launch
    LaunchedEffect(key1 = true, block = {
        // start animation
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                })
        )
        // go to homeScreen following the animation
        navController.navigate(route = "${WeatherScreens.HomeScreen.name}/$defaultCity"){
            popUpTo(WeatherScreens.SplashScreen.name) {
                inclusive = true
            }
        }
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sun_icon",
                modifier = Modifier.size(95.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.LightGray
            )
        }
    }
}