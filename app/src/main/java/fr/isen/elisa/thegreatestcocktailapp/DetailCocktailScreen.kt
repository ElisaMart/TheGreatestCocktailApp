package fr.isen.elisa.thegreatestcocktailapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.isen.elisa.thegreatestcocktailapp.data.FavoritesManager
import fr.isen.elisa.thegreatestcocktailapp.model.Drink
import fr.isen.elisa.thegreatestcocktailapp.network.RetrofitInstance

@Composable
fun DetailCocktailScreen(idDrink: String) {
    var drink by remember { mutableStateOf<Drink?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(idDrink) {
        try {
            drink = RetrofitInstance.api.getCocktailById(idDrink).drinks?.firstOrNull()
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        drink != null -> {
            DetailCocktailContent(drink = drink!!)
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cocktail not found")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailContent(drink: Drink) {
    val context = LocalContext.current
    var isFavorite by remember(drink.idDrink) {
        mutableStateOf(FavoritesManager.isFavorite(context, drink.idDrink))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cocktail details") },
                actions = {
                    IconButton(onClick = {
                        FavoritesManager.toggleFavorite(context, drink.idDrink)
                        isFavorite = FavoritesManager.isFavorite(context, drink.idDrink)
                        Toast.makeText(
                            context,
                            if (isFavorite) "Added to favorites" else "Removed from favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = drink.strDrinkThumb,
                contentDescription = drink.strDrink,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = drink.strDrink ?: "Unknown cocktail",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Categories: ${drink.strCategory ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Glass: ${drink.strGlass ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    drink.ingredientsList().forEach {
                        Text("• $it")
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(drink.strInstructions ?: "No instructions")
                }
            }
        }
    }
}