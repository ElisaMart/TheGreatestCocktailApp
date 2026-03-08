package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.isen.elisa.thegreatestcocktailapp.data.FavoritesManager
import fr.isen.elisa.thegreatestcocktailapp.model.Drink
import fr.isen.elisa.thegreatestcocktailapp.network.NetworkManager
import fr.isen.elisa.thegreatestcocktailapp.ui.theme.CreamCard
import fr.isen.elisa.thegreatestcocktailapp.ui.theme.OrangeMain

@Composable
fun DetailCocktailScreen(idDrink: String) {
    var drink by remember { mutableStateOf<Drink?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(idDrink) {
        try {
            drink = NetworkManager.api.getCocktailById(idDrink).drinks?.firstOrNull()
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
                Text("Cocktail introuvable")
            }
        }
    }
}

@Composable
fun DetailCocktailContent(drink: Drink) {
    val context = LocalContext.current
    var isFavorite by remember(drink.idDrink) {
        mutableStateOf(FavoritesManager.isFavorite(context, drink.idDrink))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppHeader(title = "Détails du Cocktail")

        MainCreamCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        FavoritesManager.toggleFavorite(context, drink.idDrink)
                        isFavorite = FavoritesManager.isFavorite(context, drink.idDrink)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = OrangeMain
                    )
                }
            }

            AsyncImage(
                model = drink.strDrinkThumb,
                contentDescription = drink.strDrink,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = drink.strDrink ?: "Unknown cocktail",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Catégorie : ${drink.strCategory ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Verre : ${drink.strGlass ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = CreamCard)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Ingrédients",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    drink.ingredientsList().forEach {
                        Text("• $it", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = CreamCard)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = drink.strInstructions ?: "No instructions",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}