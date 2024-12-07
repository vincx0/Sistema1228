package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.davidchura.sistema1228.MainActivity
import com.davidchura.sistema1228.client.ClientActivity
import com.davidchura.sistema1228.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.utils.UserStore
import com.davidchura.sistema1228.utils.usuarioActivo
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var showAlertDialog by remember { mutableStateOf(false) }

            Sistema1228Theme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Bienvenido ${usuarioActivo.getString("nombres")}")
                    Button(onClick = { showAlertDialog = true }) {
                        Text("Cerrar sesion")
                    }
                    Button(onClick = {
                        startActivity(Intent(this@ProfileActivity, ClientActivity::class.java))
                    }) {
                        Text("Mostrar Clientes")
                    }

                    if (showAlertDialog) {


                        AlertDialog(
                            onDismissRequest = { },
                            title = {
                                Text(
                                    "¿Seguro que quieres cerrar sesión?",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            text = { Text("¿Esta seguro que desea cerrar sesión? si lo hace necesitara iniciar sesion de nuevo") },
                            icon = {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Warning", tint = Color.Red

                                )
                            },
                            confirmButton = {
                                ElevatedButton(
                                    onClick = {
                                        cerrarSesion()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFB22222),
                                        contentColor = Color.White
                                    )

                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Perfil",
                                        tint = Color(0xFFE0E0E0)
                                    )
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    Text("Cerrar Sesión ")

                                }
                            },
                            dismissButton = {
                                ElevatedButton(
                                    onClick = {
                                        showAlertDialog = false
                                    }, modifier = Modifier.fillMaxWidth(),

                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFB0BEC5), // Gris oscuro
                                        contentColor = Color.White
                                    )

                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Perfil",
                                        tint = Color(0xFFE0E0E0)
                                    )
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    Text("Cancelar")
                                }
                            },

                            shape = RoundedCornerShape(12.dp),
                            containerColor = MaterialTheme.colorScheme.surface,


                            )

                    }

                }
            }
        }
    }

    private fun cerrarSesion() {
        val userStore = UserStore(this)
        lifecycleScope.launch {
            userStore.guardarDatosUsuario("")
            finish()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        }
    }
}
