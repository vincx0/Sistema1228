package com.davidchura.sistema1228.client

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidchura.sistema1228.client.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.dao.Cliente
import com.davidchura.sistema1228.dao.ClienteDao
import com.davidchura.sistema1228.dao.DatabaseProviders

class ClientActivity : ComponentActivity() {
    private lateinit var clienteDao: ClienteDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProviders.getDatabase(this)

        clienteDao = database.clienteDao()

        enableEdgeToEdge()
        setContent {

            val clientesList = remember { mutableStateOf(listOf<Cliente>()) }
            LaunchedEffect(Unit) {
                clienteDao.getAllClients().collect { clientes ->
                    clientesList.value = clientes
                }
            }

            Scaffold(modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        startActivity(Intent(this, ClientsInsertActivity::class.java))
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
                    }


                }

            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    LazyColumn {
                        items(clientesList.value) { cliente ->
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        val intent = Intent(this@ClientActivity, ClientUpdateActivity::class.java)
                                        intent.putExtra("clienteId", cliente.id)
                                        intent.putExtra("clienteNombre", cliente.name)
                                        intent.putExtra("clienteApellido", cliente.lastName)
                                        intent.putExtra("clienteEdad", cliente.age)
                                        intent.putExtra("clienteTelefono", cliente.phone)
                                        intent.putExtra("clienteDireccion", cliente.address)
                                        intent.putExtra("clienteEmail", cliente.email)
                                        startActivity(intent)
                                    }
                                    .padding(16.dp) // Espaciado interior
                                    .fillMaxWidth()
                                    .background(Color(0xFFF0F4F8)) // Fondo suave

                                    .shadow(4.dp) // Sombra sutil
                            ) {
                                // Concatenación de nombre y apellido con un ícono
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = "Perfil",
                                        tint = Color(0xFF0D47A1) // Azul
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${cliente.name} ${cliente.lastName}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color(0xFF0D47A1) // Azul
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))

                                // Edad con ícono
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Edad",
                                        tint = Color(0xFF0D47A1) // Azul
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${cliente.age} años",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF607D8B) // Gris más suave
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))

                                // Teléfono con ícono
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Phone,
                                        contentDescription = "Teléfono",
                                        tint = Color(0xFF0D47A1) // Azul
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = cliente.phone,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF607D8B) // Gris más suave
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))

                                // Dirección con ícono
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = "Dirección",
                                        tint = Color(0xFF0D47A1) // Azul
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = cliente.address,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF607D8B) // Gris más suave
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))

                                // Email con ícono
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Email,
                                        contentDescription = "Email",
                                        tint = Color(0xFF0D47A1) // Azul
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = cliente.email,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF607D8B) // Gris más suave
                                    )
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                thickness = 1.dp,
                                color = Color.Gray
                            ) // Línea divisoria
                        }
                    }
                }


            }

        }
    }
}
