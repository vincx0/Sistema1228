package com.davidchura.sistema1228.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserStore(private val context: Context) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("datos")
        val DATOS_USUARIO = stringPreferencesKey("datos_usuario")
    }

    val leerDatosUsuario: Flow<String> = context.dataStore.data
        .map {
            it[DATOS_USUARIO] ?: ""
        }
    suspend fun guardarDatosUsuario(infoUsuario: String) {
        context.dataStore.edit {
            it[DATOS_USUARIO] = infoUsuario
        }
    }

}