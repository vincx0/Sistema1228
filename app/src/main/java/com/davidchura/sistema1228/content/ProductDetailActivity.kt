package com.davidchura.sistema1228.content

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.R
import com.davidchura.sistema1228.ui.theme.Sistema1228Theme
import com.davidchura.sistema1228.utils.BASE_URL
import org.json.JSONArray

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras // Asi obtienen los datos enviados desde
        // otro activity por intent
        val idproducto = bundle!!.getString("idproducto")
        val queue = Volley.newRequestQueue(this)
        val url = BASE_URL + "productos.php?idproducto=$idproducto"
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

    @OptIn(ExperimentalMaterial3Api::class)
    private fun fillArray(response: String?) {
        val jsonArray = JSONArray(response)
        val idproducto = jsonArray.getJSONObject(0).getString("idproducto")
        val nombre = jsonArray.getJSONObject(0).getString("nombre")
        val detalle = jsonArray.getJSONObject(0).getString("detalle")
        val categoria = jsonArray.getJSONObject(0).getString("categoria")
        val imagengrande = jsonArray.getJSONObject(0).getString("imagengrande")
        val descripcion = jsonArray.getJSONObject(0).getString("descripcion")
        setContent {
            val scrollState = rememberScrollState()
            Sistema1228Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            title = {
                                Text(nombre)
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
                    }) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(scrollState)) {
                        Column {

                            AsyncImage(
                                model = "https://servicios.campus.pe/" + imagengrande,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {

                                Text("SKU " + idproducto, color = Color.Gray)
                                Text(
                                    nombre + " " + detalle, color = Color.Black,
                                    style = MaterialTheme.typography.displaySmall,
                                )


                                Text(categoria, color = Color.Gray)
                                Text("")
                                if (descripcion.isNotEmpty()) {
                                    Text("Descripción", style = MaterialTheme.typography.bodyMedium)
                                }else  {
                                    Text("Descripción no disponible")
                                }
                                Text(
                                    text = HtmlCompat.fromHtml(
                                        descripcion,
                                        HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
                                    ).toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
