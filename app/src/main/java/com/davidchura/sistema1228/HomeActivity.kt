package com.davidchura.sistema1228

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidchura.sistema1228.content.DirectorsActivity
import com.davidchura.sistema1228.content.EmployeesActivity
import com.davidchura.sistema1228.content.StoreActivity
import com.davidchura.sistema1228.content.SuppliersActivity
import com.davidchura.sistema1228.ui.theme.Sistema1228Theme

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val etiquetas = arrayOf("Proveedores", "Empleados", "Tienda",
            "Directores","Clientes","Salir")
        //val iconos = intArrayOf(Icons.Default.Add,)
        val iconos = arrayOf(
            Icons.Default.Build,
            Icons.Default.Person,
            Icons.Default.ShoppingCart,
            Icons.Default.Notifications,
            Icons.Default.AccountBox,
            Icons.Sharp.Close
        )
        setContent {
            Sistema1228Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(stringResource(id = R.string.home))
                            }
                        )
                    },
                ) { innerPadding ->
                    Column (modifier = Modifier.padding(innerPadding)){
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp), // Padding alrededor de la cuadrícula
                            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaciado horizontal entre columnas
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado vertical entre filas
                        ) {
                            items(etiquetas.size){
                                Card (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .clickable {
                                            mostrarVentana(it)
                                        },
                                    elevation = CardDefaults.cardElevation(4.dp), // Sombra de 4dp
                                    colors = CardDefaults.cardColors(containerColor = Color.White) // Color de fondo blanco
                                ){
                                    Column (
                                        modifier = Modifier
                                            .padding(all = dimensionResource(id = R.dimen.size_2))
                                    ){
                                        Icon(
                                            imageVector = iconos[it],
                                            contentDescription = null, // Proporciona una descripción si es necesario
                                            modifier = Modifier.size(24.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )

                                        Text(text = etiquetas[it])
                                    }
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun mostrarVentana(it: Int) {
        Log.d("VERIFICAR", it.toString())
        when(it){
            0 -> startActivity(Intent(this, SuppliersActivity::class.java))
            1 -> startActivity(Intent(this, EmployeesActivity::class.java))
            2 -> startActivity(Intent(this, StoreActivity::class.java))
            3 -> startActivity(Intent(this, DirectorsActivity::class.java))

            else -> finish()
        }
    }
}