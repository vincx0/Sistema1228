package com.davidchura.sistema1228.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert
    suspend fun insertCliente(cliente: Cliente)

    @Update
    suspend fun updateCliente(cliente: Cliente)

    @Delete
    suspend fun deleteCliente(cliente: Cliente)

    @Query("SELECT * FROM clientes")
    fun getAllClients(): Flow<List<Cliente>>


}