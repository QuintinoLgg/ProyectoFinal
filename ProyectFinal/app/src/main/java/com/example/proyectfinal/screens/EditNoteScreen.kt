package com.example.proyectfinal.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyectfinal.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectfinal.Constants
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.utils.NotesAppNavigationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentEditNote(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Content(viewModel, navController, navigationType)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    //Variable de ViewModel
    val miViewModel = viewModel<MainViewModel>()

    Scaffold (
        topBar = {
            var MyTitle = stringResource(id = R.string.editar_nota)
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
                    var selectedItem by remember { mutableStateOf(2) }
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
                    var selectedItem by remember { mutableStateOf(2) }
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
                            var selectedItem by remember { mutableStateOf(2) }
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
                        UI(viewModel, miViewModel, navController)
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
                UI(viewModel, miViewModel, navController)
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
                UI(viewModel, miViewModel, navController)
            }
        }
        // PARA PANTALLAS EXTENSAS, EL CONTENIDO SE INCLUYE CON EL NAVIGATION DRAWER

    }
}


@Composable
private fun UI(viewModel: miViewModel, miViewModel: MainViewModel, navController: NavController){
    val scope = rememberCoroutineScope()

    val currentTitulo = remember { mutableStateOf("") }
    val currentDescripcion = remember { mutableStateOf("") }
    val note = Constants.General.nota

    LaunchedEffect(true){
        scope.launch(Dispatchers.IO){
            currentTitulo.value = note.titulo
            currentDescripcion.value = note.descripcion
        }
    }

    LazyColumn(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item{
            // CAJA DE TEXTO DE TITULO
            TextField(
                value = currentTitulo.value,
                onValueChange = { value ->
                    currentTitulo.value = value
                },
                label = { Text(stringResource(id = R.string.titulo)) },
                placeholder = { Text(stringResource(id = R.string.agregar_titulo)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            // BOTONES DE MULTIMEDIA
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.apartado_multimedia),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Row {
                    // BOTON DE GALERIA
                    Button(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            Icons.Filled.Image,
                            contentDescription = "Galería",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // BOTON DE GRABADORA
                    Button(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Micrófono",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // BOTON DE OPCIONES
                    Button(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            // CAJA DE TEXTO PARA LA DESCRIPCI[ON
            TextField(
                value = currentDescripcion.value,
                onValueChange = { value ->
                    currentDescripcion.value = value
                },
                label = { Text(stringResource(id = R.string.descripcion)) },
                placeholder = { Text(stringResource(id = R.string.agregar_descripcion)) }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            // BOTÓN GUARDAR Y CANCELAR
            Row {
                //val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
                // BOTON DE GUARDAR
                Button(
                    onClick = {
                        viewModel.updateNote(
                            Note(
                                id = note.id,
                                titulo = currentTitulo.value,
                                descripcion = currentDescripcion.value
                            )
                        )
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceTint,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Guardar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(stringResource(id = R.string.guardar))
                }
                Spacer(modifier = Modifier.width(10.dp))
                // BOTON DE CANCELAR
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Cancelar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(stringResource(id = R.string.cancelar))
                }
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditNoteScreen(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentEditNote(viewModel, navController, navigationType)
    }
}