package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.app.Notification.Style
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectfinal.R
import com.example.proyectfinal.navigation.AppScreens
import com.example.proyectfinal.ui.theme.ProyectFinalTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign




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
            TopBar(navController)
            Spacer(modifier = Modifier.height(10.dp))
            Body(navController)
            Footer()
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController){
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
}

//Cuerpo del diseño
@Composable
fun Body(navController: NavController){
    //Variables
    var Name by remember{ mutableStateOf("") }
    var Affair by remember{ mutableStateOf("") }
    var dateElement by remember{ mutableStateOf("") }
    var search by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.92f)
            .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp)
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
                Tarjeta(titulo = "Puros Cadetes de Linares", asunto = stringResource(id = R.string.tarea), fecha = "01/01/2024")
                Tarjeta(titulo = "Arriba Ramón Ayala", asunto = stringResource(id = R.string.tarea), fecha = "01/01/2024")
                Tarjeta(titulo = "Y que siga la mata dando viejooo", stringResource(id = R.string.nota), fecha = "28/08/2024")
                Tarjeta(titulo = "Márquele compa nitro", asunto = stringResource(id = R.string.tarea), fecha = "13/12/2021")
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Footer(){
    //Variables
    var showMenuDate by  remember{ mutableStateOf(false) }
    val showDate = remember {
        mutableStateOf("Todos")
    }
    TopAppBar(
        navigationIcon = {
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { /*TODO*/ }
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
                    Text(text = stringResource(id = R.string.fecha),
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(.80f)
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Fecha",
                        modifier = Modifier.size(45.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
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
                                showDate.value = "Hoy"
                                showMenuDate = !showMenuDate
                            }) {
                            Icon(imageVector = Icons.Filled.DateRange,
                                contentDescription = "Fecha")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = stringResource(id = R.string.hoy))
                        }
                        DropdownMenuItem(
                            onClick = {
                                showDate.value = "Semana"
                                showMenuDate = !showMenuDate
                            }) {
                            Icon(imageVector = Icons.Filled.DateRange,
                                contentDescription = "Fecha")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = stringResource(id = R.string.semana))
                        }
                        DropdownMenuItem(
                            onClick = {
                                showDate.value = "Mes"
                                showMenuDate = !showMenuDate
                            }){
                            Icon(imageVector = Icons.Filled.DateRange,
                                contentDescription = "Fecha")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = stringResource(id = R.string.mes))
                        }
                        DropdownMenuItem(
                            onClick = {
                                showDate.value = "Todos"
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tarjeta(titulo: String, asunto: String, fecha: String){
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
        Column (
            modifier = Modifier
                .padding(start = 10.dp, top = 4.dp, bottom = 8.dp)
                .align(Alignment.Start)
        ) {
            Text(
                text = titulo,
                modifier = Modifier
                    .padding(top = 6.dp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
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


