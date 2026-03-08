package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.elisa.thegreatestcocktailapp.data.FavoritesManager
import fr.isen.elisa.thegreatestcocktailapp.model.Drink
import fr.isen.elisa.thegreatestcocktailapp.network.NetworkManager

@Composable
fun FavoritesScreen(
    onCocktailClick: (String) -> Unit
) {
    val context = LocalContext.current
    var favoriteDrinks by remember { mutableStateOf(listOf<Drink>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        try {
            val ids = FavoritesManager.getFavorites(context).toList()
            val list = mutableListOf<Drink>()

            ids.forEach { id ->
                try {
                    val drink = NetworkManager.api.getCocktailById(id).drinks?.firstOrNull()
                    if (drink != null) {
                        list.add(drink)
                    }
                } catch (_: Exception) {
                }
            }

            favoriteDrinks = list

            if (ids.isEmpty()) {
                errorMessage = "Aucun favori pour le moment"
            }
        } catch (e: Exception) {
            errorMessage = "Erreur de chargement : ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader(title = "Favoris")

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            favoriteDrinks.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MainCreamCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "Aucun favori pour le moment",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            else -> {
                MainCreamCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(favoriteDrinks) { drink ->
                            MainCreamCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCocktailClick(drink.idDrink) },
                                contentPadding = PaddingValues(14.dp)
                            ) {
                                Text(
                                    text = drink.strDrink ?: "Unknown",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}