package com.davidchura.sistema1228.client

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.davidchura.sistema1228.client.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.dao.Cliente
import com.davidchura.sistema1228.dao.DatabaseProviders
import kotlinx.coroutines.launch

class ClientsInsertActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var name by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var age by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var phone by remember { mutableStateOf("") }

            Sistema1228Theme() {
                Box(modifier = Modifier.fillMaxSize()) {

                    Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                colors = topAppBarColors(
                                    containerColor = Color.White,
                                    titleContentColor = Color.Black,
                                ),
                                title = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Nuevo Cliente")
                                        Button(
                                            onClick = {
                                                val database =
                                                    DatabaseProviders.getDatabase(this@ClientsInsertActivity)
                                                val clienteDao = database.clienteDao()
                                                lifecycleScope.launch {
                                                    val user = Cliente(
                                                        name = name,
                                                        lastName = lastName,
                                                        email = email,
                                                        age = age,
                                                        phone = phone,
                                                        address = address
                                                    )
                                                    clienteDao.insertCliente(user)
                                                    finish()
                                                }
                                            },

                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF007BFF),
                                                contentColor = Color.White
                                            )

                                        ) {
                                            Text("Guardar")
                                        }
                                    }

                                },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack, null,
                                            tint = Color.Black
                                        )
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->

                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(32.dp)
                                .fillMaxSize()
                                ,
                        ) {

                            OutlinedTextField(
                                value = name, onValueChange = { name = it },
                                label = { Text("Nombre") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                            OutlinedTextField(
                                value = lastName, onValueChange = { lastName = it },
                                label = { Text("Apellido") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                            OutlinedTextField(
                                value = email, onValueChange = { email = it },
                                label = { Text("Email") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                            OutlinedTextField(
                                value = age, onValueChange = { age = it },
                                label = { Text("Edad") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                            OutlinedTextField(
                                value = phone, onValueChange = { phone = it },
                                label = { Text("Telefono") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                            OutlinedTextField(
                                value = address, onValueChange = { address = it },
                                label = { Text("Direccion") },
                                modifier = Modifier.fillMaxWidth()
                            )


                        }
                    }
                }
            }
        }
    }
}

