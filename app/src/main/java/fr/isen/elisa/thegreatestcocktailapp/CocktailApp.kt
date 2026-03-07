package fr.isen.elisa.thegreatestcocktailapp

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.elisa.thegreatestcocktailapp.ui.theme.CreamCard
import fr.isen.elisa.thegreatestcocktailapp.ui.theme.OrangeMain
import fr.isen.elisa.thegreatestcocktailapp.ui.theme.PeachBackground

sealed class Screen(val route: String, val label: String) {
    data object Random : Screen("random", "Cocktail")
    data object Categories : Screen("categories", "Catégories")
    data object Favorites : Screen("favorites", "Favoris")
    data object Drinks : Screen("drinks/{category}", "Drinks") {
        fun createRoute(category: String) = "drinks/${Uri.encode(category)}"
    }
    data object Detail : Screen("detail/{idDrink}", "Detail") {
        fun createRoute(idDrink: String) = "detail/$idDrink"
    }
}

@Composable
fun CocktailApp() {
    val navController = rememberNavController()

    val bottomScreens = listOf(
        Screen.Random,
        Screen.Categories,
        Screen.Favorites
    )

    AppBackground {
        Scaffold(
            containerColor = PeachBackground.copy(alpha = 0f),
            bottomBar = {
                NavigationBar(
                    containerColor = CreamCard.copy(alpha = 0.97f)
                ) {
                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                    bottomScreens.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Random.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = OrangeMain,
                                selectedTextColor = OrangeMain,
                                indicatorColor = CreamCard,
                                unselectedIconColor = OrangeMain.copy(alpha = 0.6f),
                                unselectedTextColor = OrangeMain.copy(alpha = 0.6f)
                            ),
                            icon = {
                                when (screen) {
                                    Screen.Random -> Icon(Icons.Default.Home, contentDescription = null)
                                    Screen.Categories -> Icon(Icons.Default.List, contentDescription = null)
                                    Screen.Favorites -> Icon(Icons.Default.Favorite, contentDescription = null)
                                    else -> {}
                                }
                            },
                            label = { Text(screen.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Random.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Random.route) {
                    RandomCocktailScreen()
                }

                composable(Screen.Categories.route) {
                    CategoriesScreen(
                        onCategoryClick = { category ->
                            navController.navigate(Screen.Drinks.createRoute(category))
                        }
                    )
                }

                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        onCocktailClick = { idDrink ->
                            navController.navigate(Screen.Detail.createRoute(idDrink))
                        }
                    )
                }

                composable(
                    route = Screen.Drinks.route,
                    arguments = listOf(navArgument("category") { type = NavType.StringType })
                ) { backStackEntry ->
                    val encodedCategory = backStackEntry.arguments?.getString("category") ?: ""
                    val category = Uri.decode(encodedCategory)

                    DrinksScreen(
                        category = category,
                        onCocktailClick = { idDrink ->
                            navController.navigate(Screen.Detail.createRoute(idDrink))
                        }
                    )
                }

                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("idDrink") { type = NavType.StringType })
                ) { backStackEntry ->
                    val idDrink = backStackEntry.arguments?.getString("idDrink") ?: ""
                    DetailCocktailScreen(idDrink = idDrink)
                }
            }
        }
    }
}