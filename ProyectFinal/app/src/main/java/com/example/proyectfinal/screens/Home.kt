package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectfinal.R
import com.example.proyectfinal.navigation.AppScreens
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectfinal.ui.theme.MainViewModel


//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentHome(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
                //.align(Alignment.Center)
                //.background(colorResource(id = R.color.Secundario))
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            desing(navController)
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun desing(navController: NavController){

    //Variable para el ViewModel
    val miViewModel = viewModel<MainViewModel>()

    Scaffold (
        topBar = {
            var MyTitle = stringResource(id = R.string.agenda)
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú", modifier = Modifier.size(40.dp))
                    }
                },
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = MyTitle,
                            modifier = Modifier.align(Alignment.CenterVertically))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        //Diseño de la parte inferior
        bottomBar = {
            //Variables
            var showMenuDate by  remember{ mutableStateOf(false) }
            val showDate = remember {
                mutableStateOf("Todos")
            }
            TopAppBar(
                navigationIcon = {
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = { showMenuDate = !showMenuDate  }
                    ) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Fecha", modifier = Modifier.size(40.dp))
                    }
                },
                title = {
                    TextButton(onClick = { showMenuDate = !showMenuDate },
                        modifier = Modifier.fillMaxSize(.96f)
                    ) {
                        Row (
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            Text(text = showDate.value,
                                fontSize = 30.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth(.80f)
                                    .align(Alignment.CenterVertically)
                            )

                            Icon(
                                Icons.Filled.KeyboardArrowUp,
                                contentDescription = "Fecha",
                                modifier = Modifier
                                    .size(45.dp)
                                    .align(Alignment.CenterVertically),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(Alignment.Bottom)) {
                            DropdownMenu(
                                expanded = showMenuDate,
                                onDismissRequest = { showMenuDate = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(.2f)
                                    .border(1.dp, Color.Black)
                                    .align(Alignment.BottomEnd)
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        miViewModel.date = "Hoy"
                                        showDate.value = miViewModel.date
                                        showMenuDate = !showMenuDate
                                    }) {
                                    Icon(imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Fecha")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = stringResource(id = R.string.hoy))
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        miViewModel.date = "Semana"
                                        showDate.value = miViewModel.date
                                        showMenuDate = !showMenuDate
                                    }) {
                                    Icon(imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Fecha")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = stringResource(id = R.string.semana))
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        miViewModel.date = "Mes"
                                        showDate.value = miViewModel.date
                                        showMenuDate = !showMenuDate
                                    }){
                                    Icon(imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Fecha")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = stringResource(id = R.string.mes))
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        miViewModel.date = "Todos"
                                        showDate.value = miViewModel.date
                                        showMenuDate = !showMenuDate
                                    }){
                                    Icon(imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Fecha")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = stringResource(id = R.string.todos))
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ){
        //Variables
        var Name by remember{ mutableStateOf("") }
        var Affair by remember{ mutableStateOf("") }
        var dateElement by remember{ mutableStateOf("") }
        var search by remember { mutableStateOf("") }

        //Listas
        val titulos = listOf(
            "Puros Cadetes de Linares",
            "Arriba Ramón Ayala no se quien sea",
            "Y que siga la mata",
            "Márquele compa nitr no se quien seao"
        )
        val asuntos = listOf(
            "Tarea",
            "Tarea",
            "Nota",
            "Tarea"
        )
        val fechas = listOf(
            "01/01/2024",
            "01/01/2024",
            "28/08/2024",
            "13/12/2021"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.92f)
                .padding(start = 20.dp, top = 80.dp, end = 20.dp, bottom = 0.dp)
        ){
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.9f)
                    .padding(4.dp)
            ){
                // Botón de búsqueda
                item {
                    OutlinedTextField(
                        value = search,
                        onValueChange = {search = it},
                        label = { Text(text = stringResource(id = R.string.buscar)) },
                        placeholder = { Text(text = stringResource(id = R.string.buscar)) },
                        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar") }
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
                item {
                    // LISTADO DE DESTACADOS
                    Row {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Destacados")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(id = R.string.destacados), style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Tarjeta(titulo = "ARRIBA EL AMÉRICA", asunto = stringResource(id = R.string.tarea), fecha = "26/05/2013")
                    Tarjeta(titulo = "abajo las chivazzz", asunto = stringResource(id = R.string.nota), fecha = "03/10/2023")
                    Spacer(modifier = Modifier.height(25.dp))
                }
                item {
                    // LISTADO DE TARJETAS NORMALES
                    Row {
                        Icon(imageVector = Icons.Filled.Note, contentDescription = stringResource(id = R.string.notas_tareas))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(id = R.string.notas_tareas), style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                  }
                items(titulos.size) { index ->
                    Tarjeta(titulo = titulos[index], asunto = asuntos[index], fecha = fechas[index])
                }
            }
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(route = AppScreens.AddEditNoteScreen.route) },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Nuevo asunto") },
                text = { Text(text = stringResource(id = R.string.agregar)) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, start = 20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tarjeta(titulo: String, asunto: String, fecha: String){
    var showMenuAffair by  remember{ mutableStateOf(false) }

    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f),
        onClick = { /*TODO*/ }
    ) {
        Row(modifier = Modifier.fillMaxSize()){
            Column (
                modifier = Modifier
                    .padding(start = 10.dp, top = 4.dp, bottom = 8.dp)
                    .fillMaxWidth(0.8f)

            ) {
                Text(
                    text = titulo,
                    modifier = Modifier
                        .padding(top = 6.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = asunto,
                    modifier = Modifier
                        .padding(2.dp),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = fecha,
                    modifier = Modifier
                        .padding(2.dp),
                    textAlign = TextAlign.Start
                )
            }
            IconButton(onClick = { showMenuAffair = !showMenuAffair }, modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterVertically)) {
                Icon(painter = painterResource(id = R.drawable.points), contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                        .fillMaxSize())
            }
        }
    }
    //Menu desplegable de cada asunto
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(start = 120.dp)) {
        DropdownMenu(
            expanded = showMenuAffair,
            onDismissRequest = { showMenuAffair = false },
            modifier = Modifier
                .fillMaxWidth(.45f)
                .border(1.dp, Color.Black)
        ) {
            DropdownMenuItem(
                onClick = {
                    showMenuAffair = !showMenuAffair
                }) {
                Icon(
                    imageVector = Icons.Filled.Handshake,
                    contentDescription = "Terminado"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.terminado))
            }
            DropdownMenuItem(
                onClick = {
                    showMenuAffair = !showMenuAffair
                }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Destacar"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.destacado))
            }
            DropdownMenuItem(
                onClick = {
                    showMenuAffair = !showMenuAffair
                }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.editar))
            }
            DropdownMenuItem(
                onClick = {
                    showMenuAffair = !showMenuAffair
                }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.eliminar))
            }

        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController){
    Scaffold {
        BodyContentHome(navController)
    }
}




