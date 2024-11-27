package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AlphabetIndexer
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.content.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.utils.BASE_URL
import com.davidchura.sistema1228.utils.UserStore
import com.davidchura.sistema1228.utils.usuarioActivo
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class LoginActivity : ComponentActivity() {

    var checkSaveSession = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var usuario by remember { mutableStateOf("") }
            var clave by remember { mutableStateOf("") }
            var checkboxState by remember { mutableStateOf(false) }

            Sistema1228Theme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Iniciar Sesión", style = MaterialTheme.typography.titleLarge)
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = clave,
                        onValueChange = { clave = it },
                        label = { Text("Clave") },
                        modifier = Modifier.padding(vertical = 8.dp),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row( verticalAlignment = Alignment.CenterVertically){
                        Text("Desea Guardar la sesion?")
                        Checkbox(checked = checkboxState , onCheckedChange = {
                            checkboxState = it
                            checkSaveSession = it
                        })
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        loginService(usuario, clave)
                    }) {
                        Text("Iniciar Sesión")
                    }

                }

            }
        }
    }

    private fun loginService(usuario: String, clave: String) {
        Log.d("Login", "Usuario: $usuario, clave: $clave")

        val queue = Volley.newRequestQueue(this)

        val url = BASE_URL + "/iniciarsesion.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("API Response", response)
                verifyLogin(response)

            },
            {}) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["usuario"] = usuario
                params["clave"] = clave
                return params

            }
        }
        queue.add(stringRequest)
    }

    private fun verifyLogin(response: String) {
        when (response) {
            "-1" -> Toast.makeText(this, "El usuario no existe", Toast.LENGTH_LONG)
                .show()

            "-2" -> Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
            else -> {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this, ProfileActivity::class.java))
                verifySaveSession(response)
            }
        }
    }

    private fun verifySaveSession(response: String) {

        //aqui se verifica si el check es true si es entonces en el datastore
        //view //tool windows // Device explorer // data // data // com.davidchura.sistema1228 // files
        usuarioActivo = JSONArray(response).getJSONObject(0)
        if (checkSaveSession) {
            val userStore = UserStore(this)
            lifecycleScope.launch {
                userStore.guardarDatosUsuario(response)
            }
        }
    }
}
