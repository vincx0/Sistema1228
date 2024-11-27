package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.R
import com.davidchura.sistema1228.ui.theme.Color2
import com.davidchura.sistema1228.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.utils.BASE_URL
import org.json.JSONArray

class StoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val queue = Volley.newRequestQueue(this)
        val url = BASE_URL + "categorias.php"
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
            val idcategoria = jsonArray.getJSONObject(i).getString("idcategoria")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val descripcion = jsonArray.getJSONObject(i).getString("descripcion")
            val total = jsonArray.getJSONObject(i).getString("total")
            val foto = jsonArray.getJSONObject(i).getString("foto")
            val hashMap = HashMap<String, String>()
            hashMap["idcategoria"] = idcategoria
            hashMap["nombre"] = nombre
            hashMap["descripcion"] = descripcion
            hashMap["foto"] = foto
            hashMap["total"] = total
            arrayList.add(hashMap)
        }
        arrayList.sortBy { it["idcategoria"] }
        dibujar(arrayList)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
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
                                Text(stringResource(id = R.string.title_activity_store))
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
                    Column(modifier = Modifier.padding(innerPadding)) {
                        LazyColumn {
                            items(arrayList) { category ->
                                Box(
                                    modifier = Modifier
                                        .padding(1.dp)
                                ) {

                                    AsyncImage(
                                        model = "https://servicios.campus.pe/" + category["foto"],
                                        null,
                                        Modifier

                                            .fillMaxWidth(),
                                        contentScale = ContentScale.Crop

                                    )
                                    Column(modifier = Modifier

                                        .fillMaxWidth()
                                        .height(170.dp)
                                        .background(Color(0x80000000))
                                        .clickable {
                                            selectCategory(category)
                                        }) { // Espaciado interior

                                        Row(
                                            modifier = Modifier
                                                .padding(32.dp)
                                                .fillMaxSize()
                                        ) {
                                            Text(
                                                text = category["idcategoria"].toString(),
                                                style = MaterialTheme.typography.displayLarge,
                                                color = Color.White,
                                                modifier = Modifier.width(60.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = category["nombre"].toString(),
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = category["descripcion"].toString(),
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = "Total: " + category["total"].toString(),
                                                    color = Color.White
                                                )

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectCategory(category: HashMap<String, String>) {
        val bundle = Bundle().apply {
            //Asi se encapsulan los datos para enviarlos por intent
            putString("idcategoria", category["idcategoria"].toString())
            putString("nombre", category["nombre"].toString())
        }
        val intent = Intent(this, ProductsActivity::class.java)
        intent.putExtras(bundle) //Así se envían los datos por intent
        startActivity(intent)
    }
}
