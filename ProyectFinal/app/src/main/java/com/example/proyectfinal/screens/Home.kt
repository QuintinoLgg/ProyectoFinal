package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.app.Notification.Style
import android.os.Bundle
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
            Spacer(modifier = Modifier.height(20.dp))
            Body()
            Spacer(modifier = Modifier.height(15.dp))
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(route = AppScreens.AddEditNoteScreen.route) },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Nuevo asunto") },
                text = { Text(text = "Nuevo asunto") },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Footer()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 38.dp, end = 17.dp)
        ){
            /*
            IconButton(
                onClick = {
                    navController.navigate(route = AppScreens.AddEditNoteScreen.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Icon(painter = painterResource(id = R.drawable.more),
                    contentDescription = "Nuevo asunto")
            }
             */

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController){
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
                Text(text = "Agenda")
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
fun Body(){
    //Variables
    var Name by remember{ mutableStateOf("") }
    var Affair by remember{ mutableStateOf("") }
    var dateElement by remember{ mutableStateOf("") }
    var search by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
            .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 20.dp)
    ){
        Column(
            modifier = Modifier
                //.align(Alignment.Center)
                .fillMaxWidth(1f)
                .padding(4.dp)
        ){
            // Botón de búsqueda
            OutlinedTextField(
                value = search,
                onValueChange = {search = it},
                label = { Text(text = "Buscar") },
                placeholder = { Text(text = "Buscar") },
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar") }
            )
            Spacer(modifier = Modifier.height(25.dp))

            // LISTADO DE DESTACADOS
            Row {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Destacados")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Destacados", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            Spacer(modifier = Modifier.height(4.dp))
            TarjetaDestacada(titulo = "Destacada 1", asunto = "Arriba el América", fecha = "03/10/2023")
            Spacer(modifier = Modifier.height(25.dp))

            // LISTADO DE TARJETAS NORMALES
            Row {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "Notas / Tareas")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Notas / Tareas", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            Spacer(modifier = Modifier.height(4.dp))
            TarjetaNormal(titulo = "Tarjeta Normal 1", asunto = "Puros cadetes de linares", fecha = "01/01/2024")
            TarjetaNormal(titulo = "Tarjeta Normal 2", asunto = "Puros cadetes de linares", fecha = "01/01/2024")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Footer(){
    //Variables
    var Date by remember{ mutableStateOf("") }

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
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Fecha")
                Spacer(modifier = Modifier.width(200.dp))
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Despliegue", modifier = Modifier.size(40.dp))
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

@Composable
fun TarjetaDestacada(titulo: String, asunto: String, fecha: String){
    var showMenu by  remember{ mutableStateOf(false) }
    ListItem(
        headlineContent = {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Column {
                    Text(text = titulo)
                    Text(text = asunto)
                    Text(text = fecha)
                }
                Column (
                    horizontalAlignment = Alignment.End
                ){
                    IconButton(onClick = {showMenu = !showMenu},
                        modifier = Modifier.size(55.dp),
                    ) {
                        Icon(painter = painterResource(id = R.drawable.points),
                            contentDescription = "Menu de notas y tareas",
                            modifier = Modifier.padding(top = 18.dp))
                    }
                    Box {
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier
                                .width(150.dp)
                                .border(1.dp, Color.Black)
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Completado") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Destacar") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Editar") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Eliminar") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                        }
                    }
                }

            }
        },
        leadingContent = {
            Icon(Icons.Filled.Star, contentDescription = "Descripción")
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            headlineColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun TarjetaNormal(titulo: String, asunto: String, fecha: String){
    var showMenu by  remember{ mutableStateOf(false) }
    ListItem(
        headlineContent = {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Column {
                    Text(text = titulo)
                    Text(text = asunto)
                    Text(text = fecha)
                }
                Column (
                    horizontalAlignment = Alignment.End
                ){
                    IconButton(onClick = {showMenu = !showMenu},
                        modifier = Modifier.size(55.dp),
                    ) {
                        Icon(painter = painterResource(id = R.drawable.points),
                            contentDescription = "Menu de notas y tareas",
                            modifier = Modifier.padding(top = 18.dp))
                    }
                    Box {
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier
                                .width(150.dp)
                                .border(1.dp, Color.Black)
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Completado") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Destacar") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Editar") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Eliminar") },
                                onClick = { /*TODO*/ },
                                modifier = Modifier.border(1.dp, Color.Black)
                            )
                        }
                    }
                }

            }
        },
        leadingContent = {
            Icon(Icons.Filled.Info, contentDescription = "Descripción")
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            headlineColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
}


//Para crear las lineas verticales
@Composable
fun VerticalLine() {
    Spacer(
        modifier = Modifier
            .height(2.dp)
            .width(200.dp)
            .background(Color.White)
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController){
    Scaffold {
        BodyContentHome(navController)
    }
}


