package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.R
import com.davidchura.sistema1228.content.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.ui.theme.Color2
import org.json.JSONArray
import java.util.concurrent.ConcurrentSkipListMap

class DirectorsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/directores.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                fillArray(response)
            },
            { })
        queue.add(stringRequest)
        enableEdgeToEdge()

    }

    private fun fillArray(response: String) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val iddirector = jsonArray.getJSONObject(i).getString("iddirector")
            val nombres = jsonArray.getJSONObject(i).getString("nombres")
            val peliculas = jsonArray.getJSONObject(i).getString("peliculas")
            val hashMap = HashMap<String, String>()
            hashMap["iddirector"] = iddirector
            hashMap["nombres"] = nombres
            hashMap["peliculas"] = peliculas
            arrayList.add(hashMap)
        }
        dibujar(arrayList)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            com.davidchura.sistema1228.ui.theme.Sistema1228Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            title = {
                                Text("Directores")
                            },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack, null,
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            startActivity(Intent(this, DirectorsInsertsActivity::class.java))
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        LazyColumn(
                            content = {
                                items(items = arrayList, itemContent = { director ->
                                    Surface(
                                        border = BorderStroke(1.dp, Color2),
                                        modifier = Modifier
                                            .padding(16.dp) // Espaciado exterior
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                        ) { // Espaciado interior
                                            Text(
                                                text = director["iddirector"].toString(),
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Text(
                                                text = director["nombres"].toString(),
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Text(text = director["peliculas"].toString())
                                        }
                                    }
                                })
                            }
                        )
                    }
                }
            }
        }
    }
}
