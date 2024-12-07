package com.davidchura.sistema1228.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cliente::class], version = 1, exportSchema = false)
abstract  class ClienteDataBase: RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
}