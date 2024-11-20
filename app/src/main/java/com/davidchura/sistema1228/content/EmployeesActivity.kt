package com.davidchura.sistema1228.content

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davidchura.sistema1228.R
import com.davidchura.sistema1228.ui.theme.Color2
import com.davidchura.sistema1228.ui.theme.Sistema1228Theme
import org.json.JSONArray

class EmployeesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        leerServicio()
    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/empleados.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                fillArray(response)
            },
            { })
        queue.add(stringRequest)
    }

    private fun fillArray(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val apellidos = jsonArray.getJSONObject(i).getString("apellidos")
            val nombres = jsonArray.getJSONObject(i).getString("nombres")
            val cargo = jsonArray.getJSONObject(i).getString("cargo")
            val foto = jsonArray.getJSONObject(i).getString("foto")
            val hashMap = HashMap<String, String>()
            hashMap["apellidos"] = apellidos
            hashMap["nombres"] = nombres
            hashMap["cargo"] = cargo
            hashMap["foto"] = foto
            arrayList.add(hashMap)
        }
        dibujar(arrayList)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            //calcular el tamaño de la pantalla
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp
            val density = LocalDensity.current.density
            val screnHeigthpx = screenHeight * density
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
                                Text("Empleados")
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
                    Column (modifier = Modifier.padding(innerPadding)) {
                        LazyRow(
                            content = {
                                items(items = arrayList, itemContent = { employee ->
                                    Surface(
                                        //border = BorderStroke(1.dp, Color2),
                                        modifier = Modifier
                                        // Espaciado exterior
                                    ) {
                                        Box(
                                            modifier = Modifier.fillParentMaxSize()

                                        ) { // Espaciado interior
                                            DrawEmployee(employee, screnHeigthpx)
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

@Composable
fun DrawEmployee(employee: HashMap<String, String>, screnHeigthpx: Float) {
    AsyncImage(
        model = "https://servicios.campus.pe/fotos/" + employee["foto"].toString(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                //degradado de abajo hacia arriba
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color(0xDD000000)),
                    startY = screnHeigthpx.toFloat() * 0.8f,
                    endY = screnHeigthpx.toFloat() * 1f
                )
            )

            .padding(bottom = 50.dp, start = 20.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = employee["nombres"].toString() + employee
                ["apellidos"].toString(),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Text(
            text = employee["cargo"].toString(),
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}