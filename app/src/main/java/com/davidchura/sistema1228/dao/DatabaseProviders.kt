package com.davidchura.sistema1228.dao

import android.content.Context
import androidx.room.Room


object DatabaseProviders {
    private var INSTANCIA: ClienteDataBase? = null
    fun getDatabase(context: Context): ClienteDataBase {
        return INSTANCIA ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ClienteDataBase::class.java,
                "cliente_database"
            ).build()
            INSTANCIA = instance
            instance
        }
    }
}