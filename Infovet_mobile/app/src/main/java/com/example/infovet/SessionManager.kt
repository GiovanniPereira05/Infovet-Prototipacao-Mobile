package com.example.infovet
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensão para inicializar o DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

    companion object {
        // Chaves para identificar os dados guardados
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_TOKEN = stringPreferencesKey("user_token")
        private val USER_EMAIL = stringPreferencesKey("user_email")
    }

    // Gravar dados da sessão (Função suspensa para rodar em Coroutine)
    suspend fun saveSession(token: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USER_TOKEN] = token
            preferences[USER_EMAIL] = email
        }
    }

    // Ler se o usuário está logado (Retorna um Flow observável)
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    // Ler o Token
    val userToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_TOKEN]
    }

    // Limpar sessão (Logout)
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear() // Apaga todos os dados salvos no DataStore
        }
    }
}