package com.example.littlelemon.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class UserPreferences(
    context: Context,
) {
    private val prefs =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun observeIsLoggedIn(): Flow<Boolean> = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == null || key == KEY_IS_LOGGED_IN) {
                    trySend(isLoggedIn())
                }
            }

        // Emit current value immediately.
        trySend(isLoggedIn())

        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.distinctUntilChanged()

    fun saveUser(
        firstName: String,
        lastName: String,
        email: String,
    ) {
        prefs
            .edit()
            .putString(KEY_FIRST_NAME, firstName)
            .putString(KEY_LAST_NAME, lastName)
            .putString(KEY_EMAIL, email)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun logout() {
        prefs
            .edit()
            .remove(KEY_FIRST_NAME)
            .remove(KEY_LAST_NAME)
            .remove(KEY_EMAIL)
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "little_lemon_prefs"

        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_EMAIL = "email"
    }
}
