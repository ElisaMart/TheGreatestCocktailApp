package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.isen.elisa.thegreatestcocktailapp.model.DrinkItem
import fr.isen.elisa.thegreatestcocktailapp.network.RetrofitInstance

@Composable
fun DrinksScreen(
    category: String,
    onCocktailClick: (String) -> Unit
) {
    var drinks by remember { mutableStateOf(listOf<DrinkItem>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(category) {
        try {
            drinks = RetrofitInstance.api.getDrinksByCategory(category).drinks ?: emptyList()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(drinks) { drink ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onCocktailClick(drink.idDrink) }
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        AsyncImage(
                            model = drink.strDrinkThumb,
                            contentDescription = drink.strDrink,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = drink.strDrink)
                            Text(text = "ID: ${drink.idDrink}")
                        }
                    }
                }
            }
        }
    }
}