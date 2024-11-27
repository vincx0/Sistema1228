package com.davidchura.sistema1228.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
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

class ProductsActivity : ComponentActivity() {
    var nombre = ""

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras // Asi obtienen los datos enviados desde
        // otro activity por intent
        val idcategoria = bundle!!.getString("idcategoria")
        nombre = bundle.getString("nombre").toString()

        readService(idcategoria)

        enableEdgeToEdge()

    }

    private fun readService(idcategoria: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = BASE_URL + "productos.php?idcategoria=$idcategoria"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                fillArray(response)
            },
            { })
        queue.add(stringRequest)
    }

    private fun fillArray(response: String) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val idproducto = jsonArray.getJSONObject(i).getString("idproducto")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val precio = jsonArray.getJSONObject(i).getString("precio")
            val preciorebajado = jsonArray.getJSONObject(i).getString("preciorebajado")
            val imagenchica = jsonArray.getJSONObject(i).getString("imagenchica")
            val hashMap = HashMap<String, String>()
            hashMap["idproducto"] = idproducto
            hashMap["nombre"] = nombre
            hashMap["precio"] = precio
            hashMap["preciorebajado"] = preciorebajado
            hashMap["imagenchica"] = imagenchica
            arrayList.add(hashMap)
        }
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
                                Text(nombre.toString())
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
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp), // Padding alrededor de la cuadrícula
                            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaciado horizontal entre columnas
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items = arrayList) { product ->
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            startActivity(
                                                Intent(
                                                    this@ProductsActivity,
                                                    ProductDetailActivity::class.java
                                                ).apply {
                                                    putExtra(
                                                        "idproducto",
                                                        product["idproducto"].toString()
                                                    )
                                                })
                                        }
                                        .height(300.dp),
                                    elevation = CardDefaults.cardElevation(4.dp), // Sombra de 4dp
                                    colors = CardDefaults.cardColors(containerColor = Color.White) // Color de fondo blanco
                                ) {
                                    val preciorebajado =
                                        product["preciorebajado"].toString().toFloat()
                                    val precio = product["precio"].toString().toFloat()
                                    val porcentajedescuento = (1 - preciorebajado / precio) * 100

                                    var rutafoto = ""
                                    if (product["imagenchica"] == "null")
                                        rutafoto = "https://servicios.campus.pe/imagenes/nofoto.jpg"
                                    else
                                        rutafoto =
                                            "https://servicios.campus.pe/${product["imagenchica"]}"

                                    Box {
                                        Column {

                                            AsyncImage(
                                                model = rutafoto,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            )
                                            Column (modifier = Modifier.padding(start = 16.dp)){
                                                Text(text = product["nombre"].toString())
                                                if (preciorebajado == 0f) {
                                                    Text("S/ %.2f".format(precio))
                                                } else {
                                                    Text("S/ %.2f".format(preciorebajado))
                                                    Text(
                                                        "S/ %.2f".format(precio),
                                                        textDecoration = TextDecoration.LineThrough
                                                    )
                                                }
                                            }
                                        }
                                        if (preciorebajado != 0f) {
                                            Text(
                                                "${"%.0f".format(porcentajedescuento)}%",
                                                modifier = Modifier
                                                    .background(Color.Red)
                                                    .padding(
                                                        horizontal = dimensionResource(id = R.dimen.size_2),
                                                        vertical = dimensionResource(id = R.dimen.size_1)
                                                    ),
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
