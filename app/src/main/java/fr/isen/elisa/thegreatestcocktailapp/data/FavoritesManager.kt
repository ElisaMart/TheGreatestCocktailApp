package fr.isen.elisa.thegreatestcocktailapp.data

import android.content.Context

object FavoritesManager {
    private const val PREF_NAME = "cocktail_prefs"
    private const val KEY_FAVORITES = "favorite_ids"

    fun getFavorites(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    fun isFavorite(context: Context, idDrink: String): Boolean {
        return getFavorites(context).contains(idDrink)
    }

    fun toggleFavorite(context: Context, idDrink: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = getFavorites(context).toMutableSet()

        if (current.contains(idDrink)) {
            current.remove(idDrink)
        } else {
            current.add(idDrink)
        }

        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
    }
}