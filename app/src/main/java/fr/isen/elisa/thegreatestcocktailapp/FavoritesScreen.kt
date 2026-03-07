package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.elisa.thegreatestcocktailapp.data.FavoritesManager
import fr.isen.elisa.thegreatestcocktailapp.model.Drink
import fr.isen.elisa.thegreatestcocktailapp.network.RetrofitInstance

@Composable
fun FavoritesScreen(
    onCocktailClick: (String) -> Unit
) {
    val context = LocalContext.current
    var favoriteDrinks by remember { mutableStateOf(listOf<Drink>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val ids = FavoritesManager.getFavorites(context)
        val list = mutableListOf<Drink>()

        ids.forEach { id ->
            try {
                val drink = RetrofitInstance.api.getCocktailById(id).drinks?.firstOrNull()
                if (drink != null) list.add(drink)
            } catch (_: Exception) {
            }
        }

        favoriteDrinks = list
        isLoading = false
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        favoriteDrinks.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No favorites yet")
            }
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteDrinks) { drink ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onCocktailClick(drink.idDrink) }
                    ) {
                        Box(modifier = Modifier.padding(16.dp)) {
                            Text(text = drink.strDrink ?: "Unknown")
                        }
                    }
                }
            }
        }
    }
}