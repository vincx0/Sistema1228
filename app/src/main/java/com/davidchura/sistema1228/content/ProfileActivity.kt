package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.davidchura.sistema1228.MainActivity
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

                    if (showAlertDialog) {


                        AlertDialog(
                            onDismissRequest = {  },
                            title = {
                                Text(
                                    "Cerrar sesion",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            text = { Text("¿Desea cerrar sesion?") },
                            icon = {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = "Warning"

                                )
                            },
                            confirmButton = {
                                ElevatedButton(
                                    onClick = {
                                        cerrarSesion()
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                ) {
                                    Text("Si")
                                }
                            },
                            dismissButton = {
                                ElevatedButton(
                                    onClick = {
                                        showAlertDialog = false
                                    },

                                    shape = RoundedCornerShape(12.dp),
                                ) {
                                    Text("No")
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
