package com.davidchura.sistema1228

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.content.DirectorsActivity
import com.davidchura.sistema1228.utils.BASE_URL

class DirectoresUpdateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val iddirector = bundle!!.getString("iddirector").toString()
        val nombres = bundle.getString("nombres").toString()
        val peliculas = bundle.getString("peliculas").toString()
        enableEdgeToEdge()
        setContent {
            var iddirector by remember { mutableStateOf(iddirector) }
            var nombres by remember { mutableStateOf(nombres) }
            var peliculas by remember { mutableStateOf(peliculas) }
            com.davidchura.sistema1228.content.ui.theme.Sistema1228Theme {
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
                        Text("Actualizar Director", style = MaterialTheme.typography.headlineLarge)
                        OutlinedTextField(
                            value = iddirector,
                            onValueChange = {

                            },
                            label = { Text("Codigo") }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
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
                            InsertDirector(nombres, peliculas, iddirector)
                        }) {
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }

    private fun InsertDirector(nombres: String, peliculas: String, iddirector: String) {

        val queue = Volley.newRequestQueue(this)
        val url = BASE_URL + "directoresupdate.php"

        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("API Response", response)
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DirectorsActivity::class.java))

            },
            { }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()

                params["iddirector"] = iddirector
                params["nombres"] = nombres
                params["peliculas"] = peliculas
                return params
            }
        }
        queue.add(stringRequest)
    }
}
