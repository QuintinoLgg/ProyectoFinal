package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyectfinal.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.ui.utils.NotesAppNavigationType

//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentAddEditNote(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            desingNote(navController)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun desingNote(navController: NavController){
    //Variable de ViewModel
    val miViewModel = viewModel<MainViewModel>()

    Scaffold (
        topBar = {
            var MyTitle = stringResource(id = R.string.agregar)
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
            var selectedItem by remember { mutableStateOf(2) }
            NavigationBar {
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

    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 20.dp)
        ){
            var title by remember { mutableStateOf("")}
            var description by remember { mutableStateOf("")}

            LazyColumn(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(1f)
                    .padding(start = 45.dp, end = 45.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item{
                    // CAJA DE TEXTO DE TITULO
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(stringResource(id = R.string.titulo)) },
                        placeholder = { Text(stringResource(id = R.string.agregar_titulo)) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    // COMBOBOX PARA ESCOGER NOTA O TAREA
                    val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
                    ComboBox(options, stringResource(id = R.string.asunto))
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    // DATETIMEPICKER PARA SELECCIONAR LA FECHA DE LA NOTA
                    DatePicker()
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
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(stringResource(id = R.string.descripcion)) },
                        placeholder = { Text(stringResource(id = R.string.agregar_descripcion)) }
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                }

                item {
                    // BOTÓN GUARDAR Y CANCELAR
                    Row {
                        val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
                        // BOTON DE GUARDAR
                        Button(
                            onClick = {
                                miViewModel.tittle = title
                                miViewModel.subject = options.toString()
                                miViewModel.description = description
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
    }
}




// DATETIME PICKER PERSONALIZADO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(){
    var fecha by rememberSaveable {
        mutableStateOf("")
    }
    val year: Int
    val month: Int
    val day: Int
    val nCalendar = Calendar.getInstance()
    year = nCalendar.get(Calendar.YEAR)
    month = nCalendar.get(Calendar.MONTH)
    day = nCalendar.get(Calendar.DAY_OF_MONTH)

    val nDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        {
                _, year: Int, month: Int, day: Int -> fecha = "$day/$month/$year"
        },
        year, month, day
    )

    TextField(
        value = fecha,
        onValueChange = {fecha = it},
        readOnly = true,
        label = { Text(stringResource(id = R.string.fecha)) },
        placeholder = { Text(stringResource(id = R.string.selecciona_fecha)) },
        leadingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.clickable { nDatePickerDialog.show() }
            )
        }
    )
}

// COMBOBOX PERSONALIZADO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(items: List<String>, etiqueta: String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(etiqueta) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedOptionText = item
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentAddEditNote(navController)
    }
}

