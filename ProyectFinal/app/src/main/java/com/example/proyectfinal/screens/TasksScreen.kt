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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.data.dataNotas
import com.example.proyectfinal.data.dataTareas
import com.example.proyectfinal.ui.NotesViewModel
import com.example.proyectfinal.ui.utils.NotesAppNavigationType


//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentTasksScreen(notesViewModel: NotesViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            //.align(Alignment.Center)
            //.background(colorResource(id = R.color.Secundario))
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Content(notesViewModel, navController, navigationType)
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(notesViewModel: NotesViewModel, navController: NavController, navigationType: NotesAppNavigationType){

    //Variable para el ViewModel
    val miViewModel = viewModel<MainViewModel>()

    Scaffold (
        topBar = {
            var MyTitle = stringResource(id = R.string.tareas)
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = MyTitle,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            // PARA PANTALLAS COMPACTAS, USAR UNA BARRA DE NAVEGACION INFERIOR
            if(navigationType == NotesAppNavigationType.BOTTOM_NAVIGATION){
                NavigationBar {
                    var selectedItem by remember { mutableStateOf(1) }
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.ruta)
                            },
                            label = { Text(text = item.nombre) },
                            icon = { Icon(item.icono, contentDescription = "${item.nombre} Icon") }
                        )
                    }
                }
            }
            // PARA PANTALLAS MEDIUM O MEDIANAS, USAR UN NAVIGATION RAIL
            else if(navigationType == NotesAppNavigationType.NAVIGATION_RAIL){
                NavigationRail(
                    modifier = Modifier.padding(top = 80.dp)
                ) {
                    var selectedItem by remember { mutableStateOf(1) }
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationRailItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.ruta)
                            },
                            label = { Text(text = item.nombre) },
                            icon = { Icon(item.icono, contentDescription = "${item.nombre} Icon") }
                        )
                    }
                }
            }
            // PARA PANTALLAS GRANDES O EXTENSAS, USAR UN NAVIGATION DRAWER
            else if(navigationType == NotesAppNavigationType.PERMANENT_NAVIGATION_DRAWER){
                PermanentNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier.fillMaxWidth(0.2f)
                        ) {
                            var selectedItem by remember { mutableStateOf(1) }
                            bottomNavItems.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    selected = selectedItem == index,
                                    onClick = {
                                        selectedItem = index
                                        navController.navigate(item.ruta)
                                    },
                                    label = { Text(text = item.nombre) },
                                    icon = { Icon(item.icono, contentDescription = "${item.nombre} Icon") }
                                )
                            }
                        }
                    },
                    Modifier.padding(top = 80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
                    ){
                        LazyColumn(
                            modifier = Modifier
                                .padding(4.dp)
                        ){
                            /*
                            items(dataTareas){
                                Tarjeta(titulo = it.titulo, descripcion = it.descripcion, it.fecha)
                            }

                             */

                        }
                    }
                }
            }

        }

    ){
        // CONTENIDO PARA PANTALLAS COMPACTAS
        if(navigationType == NotesAppNavigationType.BOTTOM_NAVIGATION){
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.92f)
                    .padding(start = 20.dp, top = 80.dp, end = 20.dp, bottom = 0.dp)
            ){
                LazyColumn(
                    modifier = Modifier
                        .padding(4.dp)
                ){
                    /*
                    items(dataTareas){
                        Tarjeta(titulo = it.titulo, descripcion = it.descripcion, it.fecha)
                    }
                     */

                }
            }
        }
        // CONTENIDO PARA PANTALLAS MEDIANAS
        else if(navigationType == NotesAppNavigationType.NAVIGATION_RAIL){
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.92f)
                    .padding(start = 80.dp, top = 80.dp, end = 20.dp, bottom = 20.dp)
            ){
                LazyColumn(
                    modifier = Modifier
                        .padding(4.dp)
                ){
                    /*
                    items(dataTareas){
                        Tarjeta(titulo = it.titulo, descripcion = it.descripcion, it.fecha)
                    }
                     */

                }
            }
        }
        // PARA PANTALLAS EXTENSAS, EL CONTENIDO SE INCLUYE CON EL NAVIGATION DRAWER

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Tarjeta(titulo: String, descripcion: String, fecha: String){
    var showMenuAffair by  remember{ mutableStateOf(false) }

    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
        ,
        onClick = {
            showMenuAffair = !showMenuAffair
        }
    ) {
        Column (
            modifier = Modifier
                .padding(start = 10.dp, top = 4.dp, bottom = 8.dp)
        ) {
            Text(
                text = titulo,
                modifier = Modifier
                    .padding(top = 6.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = descripcion,
                modifier = Modifier
                    .padding(2.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = fecha,
                modifier = Modifier
                    .padding(2.dp),
                textAlign = TextAlign.Center
            )
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
fun TasksScreen(notesViewModel: NotesViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentTasksScreen(notesViewModel, navController, navigationType)
    }
}