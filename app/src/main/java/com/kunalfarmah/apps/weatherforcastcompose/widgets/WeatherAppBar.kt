package com.kunalfarmah.apps.weatherforcastcompose.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.R
import com.kunalfarmah.apps.weatherforcastcompose.nav.WeatherScreens
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(), // hilt will automatically provide this, no need to pass the viewmodel manually
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val city = if(title.isNotEmpty() && title.contains(",")) title?.split(",")?.get(0)?:"" else ""
    val country = if(title.isNotEmpty() && title.contains(",")) title?.split(",")?.get(1)?:"" else ""

    val list = favoriteViewModel.favList.collectAsState().value

    val showDialog = remember {
        mutableStateOf(false)
    }

    val isContainedInFavorites = list.count{
        it.city == city
    }>0

    val isFavorite = remember {
        mutableStateOf( list.count{
            it.city == city
        }>0)
    }


    Log.d("AppBar", "WeatherAppBar: isFavorite: ${isFavorite.value}")

    if(showDialog.value){
        SettingsDropDown(navController = navController, showDropDown = showDialog)
    }
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
            IconButton(onClick = { showDialog.value = true}) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    }, navigationIcon = {
        if (isMainScreen) {
            if (icon == null) {
                IconButton(onClick = {
                    val favorite = Favorite(
                        city,
                        country
                    )
                    if(!isContainedInFavorites) {
                        favoriteViewModel.insertFavorite(favorite)
                        isFavorite.value = true
                    }
                    else{
                        favoriteViewModel.deleteFavorite(favorite)
                        isFavorite.value = false
                    }
                }) {
                    Icon(
                        imageVector =
                        if(isContainedInFavorites)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "app icon",
                        tint = Color.Red
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

@Composable
fun SettingsDropDown(navController: NavController, showDropDown: MutableState<Boolean>) {
    var expanded = remember { mutableStateOf(true) }
    val items = listOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
            modifier = Modifier
                .width(150.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = { Text(text, fontWeight = FontWeight.W300,) },
                    onClick = {
                        expanded.value = false
                        showDropDown.value = false
                        navController.navigate(
                            when(text){
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavouriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            }
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            }, contentDescription = null,
                            tint = Color.LightGray
                        )
                    }
                )
            }
        }
    }
}