package com.kunalfarmah.apps.weatherforcastcompose.widgets

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = title, color = MaterialTheme.colorScheme.secondary,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = { onAddActionClicked.invoke() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { onButtonClicked.invoke() }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    }, navigationIcon = {
        if (isMainScreen) {
            if (icon == null) {
                IconButton(onClick = {onButtonClicked.invoke()}) {
                    Icon(
                        painter = painterResource(id = R.drawable.sun),
                        contentDescription = "app icon"
                    )
                }
            } else {
                Icon(imageVector = icon,
                    contentDescription = "app icon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
        } else {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
        }
    })

}