package com.kunalfarmah.apps.weatherforcastcompose.screens.dropdown

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.nav.WeatherScreens
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.FavoriteViewModel
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, favoriteViewModel: FavoriteViewModel = hiltViewModel()){
    Scaffold(topBar = {
        WeatherAppBar(navController = navController, title = "Favorites", isMainScreen = false,
        icon= Icons.Default.ArrowBack){
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .border(2.dp, Color.Red)
            .padding(top = it.calculateTopPadding()+5.dp, start = 5.dp, end = 5.dp, bottom = it.calculateBottomPadding())) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val list = favoriteViewModel.favList.collectAsState().value
                LazyColumn(){
                    items(items = list){
                        CityRow(it, navController = navController, favoriteViewModel)
                    }
                }

            }
        }
    }

}

@Composable
fun CityRow(favorite: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel){
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(50.dp)
        .clickable {
            navController.navigate("${WeatherScreens.HomeScreen}/${favorite.city}")
        }, shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color= Color(0xFFB2DfDB)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
            Text(text = favorite.city, modifier = Modifier.padding(start=4.dp))
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape, color = Color(0xFFD1E3E1))
            {
                Text(text = favorite.country, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.bodyMedium)
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete", modifier = Modifier.clickable {
                favoriteViewModel.deleteFavorite(favorite)
            }, tint = Color.Red.copy(alpha =0.3f))
            
        }
    }
}