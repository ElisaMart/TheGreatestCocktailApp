package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.isen.elisa.thegreatestcocktailapp.model.DrinkItem
import fr.isen.elisa.thegreatestcocktailapp.network.NetworkManager

@Composable
fun DrinksScreen(
    category: String,
    onCocktailClick: (String) -> Unit
) {
    var drinks by remember { mutableStateOf(listOf<DrinkItem>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(category) {
        try {
            drinks = NetworkManager.api.getDrinksByCategory(category).drinks ?: emptyList()
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader(title = category)

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
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
                        items(drinks) { drink ->
                            MainCreamCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCocktailClick(drink.idDrink) },
                                contentPadding = PaddingValues(14.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(
                                        model = drink.strDrinkThumb,
                                        contentDescription = drink.strDrink,
                                        modifier = Modifier
                                            .size(78.dp)
                                            .clip(RoundedCornerShape(18.dp))
                                    )
                                    Spacer(modifier = Modifier.size(12.dp))
                                    Text(
                                        text = drink.strDrink,
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
}