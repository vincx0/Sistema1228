package com.davidchura.sistema1228.content

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.R
import com.davidchura.sistema1228.ui.theme.Color2
import com.davidchura.sistema1228.ui.theme.Sistema1228Theme
import org.json.JSONArray
import kotlin.math.log

class SuppliersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/proveedores.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                fillArray(response)
            },
            { error ->
                Log.d("API Error", error.toString())
                Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
        enableEdgeToEdge()

    }

    private fun fillArray(response: String) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val idproveedor = jsonArray.getJSONObject(i).getString("idproveedor")
            val nombreempresa = jsonArray.getJSONObject(i).getString("nombreempresa")
            val nombrecontacto = jsonArray.getJSONObject(i).getString("nombrecontacto")
            val cargocontacto = jsonArray.getJSONObject(i).getString("cargocontacto")
            val pais = jsonArray.getJSONObject(i).getString("pais")
            val hashMap = HashMap<String, String>()
            hashMap["idproveedor"] = idproveedor
            hashMap["nombreempresa"] = nombreempresa
            hashMap["nombrecontacto"] = nombrecontacto
            hashMap["cargocontacto"] = cargocontacto
            hashMap["pais"] = pais
            arrayList.add(hashMap)
          //  Log.d("LLEGANDO AL FILL ARRAY ", "ERROR")
        }
        dibujar(arrayList)
        Log.d("PROBLEMA", arrayList.toString() )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        Log.d("LLEGando a dibujar", arrayList.toString())
        setContent {
Text("hola adsaioeriaew")
            Sistema1228Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            title = {
                                Text(stringResource(id = R.string.title_activity_suppliers))

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
                ) { innerPadding ->
                    Text("hola mundi")
                    Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = "https://www.golosinfo.com.ua/wp-content/uploads/2023/12/408957833_670980725207980_1094025768389309263_n.jpg",
                            contentDescription = null,
                        )
                        LazyColumn(
                            content = {
                                items(items = arrayList, itemContent = { proveedor ->
                                        Log.d("IMPRIMIR PROVEEDOR ", proveedor.toString())
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
                                                text = proveedor["nombreempresa"].toString(),
                                                style = MaterialTheme.typography.titleLarge
                                            )

                                            Text(
                                                text = proveedor["nombrecontacto"].toString(),
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Text(text = proveedor["pais"].toString() + " / " + proveedor["pais"].toString())
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
