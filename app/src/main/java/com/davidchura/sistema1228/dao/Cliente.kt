package com.davidchura.sistema1228.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "clientes")
@TypeConverters(Converts::class)
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var age: String = "",
    var address: String = "",
    var phone: String = "",
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
)