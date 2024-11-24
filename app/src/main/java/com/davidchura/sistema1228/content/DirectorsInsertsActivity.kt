package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.content.ui.theme.Sistema1228Theme

class DirectorsInsertsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nombres by remember { mutableStateOf("") }
            var peliculas by remember { mutableStateOf("") }
            Sistema1228Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text("Directores")
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text("Nuevo Director", style = MaterialTheme.typography.headlineLarge)
                        OutlinedTextField(
                            value = nombres,
                            onValueChange = {
                                nombres = it
                            },
                            label = { Text("Nombres") }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        TextField(
                            value = peliculas,
                            onValueChange = {
                                peliculas = it
                            },
                            label = { Text("Peliculas") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            Log.d("API Response", "Nombres: $nombres, Peliculas: $peliculas")
                            InsertDirector(nombres, peliculas)
                        }) {
                            Text("Registrar")
                        }
                    }
                }
            }
        }
    }

    private fun InsertDirector(nombres: String, peliculas: String) {

        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/directoresinsert.php"

        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("API Response", response)
                startActivity(Intent(this, DirectorsActivity::class.java))

            },
            { }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nombres"] = nombres
                params["peliculas"] = peliculas
                return params
            }
        }
        queue.add(stringRequest)
    }
}
