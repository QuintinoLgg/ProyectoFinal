package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyectfinal.Constants
import com.example.proyectfinal.Constants.orPlaceHolderList
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.data.dataNotas
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.navigation.AppScreens
import com.example.proyectfinal.ui.NotesViewModel
import com.example.proyectfinal.ui.utils.NotesAppNavigationType


//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentNotesScreen(
    notesViewModel: NotesViewModel,
    navController: NavController,
    navigationType: NotesAppNavigationType
){
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
private fun Content(
    viewModel: NotesViewModel,
    navController: NavController,
    navigationType: NotesAppNavigationType
){
    //Variable para el ViewModel
    val miViewModel = viewModel<MainViewModel>()
    val notes = viewModel.notes.observeAsState()
    val notesToDelete = remember { mutableStateOf(listOf<Note>()) }
    val openDialog = remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            var MyTitle = stringResource(id = R.string.notas)
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
                    var selectedItem by remember { mutableStateOf(0) }
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
                    var selectedItem by remember { mutableStateOf(0) }
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
                            var selectedItem by remember { mutableStateOf(0) }
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
                        NotesList(
                            notes = notes.value.orPlaceHolderList(),
                            notesToDelete = notesToDelete,
                            openDialog = openDialog,
                            navController = navController
                        )

                        DeleteDialog(
                            openDialog = openDialog,
                            notesToDelete = notesToDelete,
                            action = {
                                notesToDelete.value.forEach{
                                    viewModel.deleteNotes(it)
                                }
                            }
                        )
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
                NotesList(
                    notes = notes.value.orPlaceHolderList(),
                    notesToDelete = notesToDelete,
                    openDialog = openDialog,
                    navController = navController
                )

                DeleteDialog(
                    openDialog = openDialog,
                    notesToDelete = notesToDelete,
                    action = {
                        notesToDelete.value.forEach{
                            viewModel.deleteNotes(it)
                        }
                    }
                )
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
                NotesList(
                    notes = notes.value.orPlaceHolderList(),
                    notesToDelete = notesToDelete,
                    openDialog = openDialog,
                    navController = navController
                )

                DeleteDialog(
                    openDialog = openDialog,
                    notesToDelete = notesToDelete,
                    action = {
                        notesToDelete.value.forEach{
                            viewModel.deleteNotes(it)
                        }
                    }
                )
            }
        }
        // PARA PANTALLAS EXTENSAS, EL CONTENIDO SE INCLUYE CON EL NAVIGATION DRAWER

    }
}


@Composable
private fun NotesList(
    notes: List<Note>,
    openDialog: MutableState<Boolean>,
    notesToDelete: MutableState<List<Note>>,
    navController: NavController
){
    LazyColumn(
        contentPadding = PaddingValues(12.dp)
    ){
        itemsIndexed(notes){index, note ->
            Tarjeta(note, notesToDelete, openDialog, navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Tarjeta(
    note: Note,
    notesToDelete: MutableState<List<Note>>,
    openDialog: MutableState<Boolean>,
    navController: NavController
){
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
        },
    ) {
        Column (
            modifier = Modifier
                .padding(start = 10.dp, top = 4.dp, bottom = 8.dp)
        ) {
            Text(
                text = note.titulo,
                modifier = Modifier
                    .padding(top = 6.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = note.descripcion,
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
                    // ACCI[ON DE EDITAR
                    Constants.NOTA_EDITAR = note.id?:0
                    navController.navigate(AppScreens.EditNoteScreen.route)
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
                    openDialog.value = true
                    notesToDelete.value = mutableListOf(note)
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


@Composable
private fun DeleteDialog(
    openDialog: MutableState<Boolean>,
    action: () -> Unit,
    notesToDelete: MutableState<List<Note>>
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Borrar nota?")
            },
            text = {
                Column() {
                    Text("Vamos a eliminar esta madre?")
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column() {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {
                                action.invoke()
                                openDialog.value = false
                                notesToDelete.value = mutableListOf()
                            }
                        ) {
                            Text("Simon")
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {
                                openDialog.value = false
                                notesToDelete.value = mutableListOf()
                            }
                        ) {
                            Text("No")
                        }
                    }

                }
            }
        )
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(notesViewModel: NotesViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentNotesScreen(notesViewModel, navController, navigationType)
    }

}

