package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.paint
import androidx.navigation.NavController
import com.example.proyectfinal.R

//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentAddEditNote(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(colorResource(id = R.color.Secundario)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            topBar(navController)
            body()
            footer()
        }
    }
}

@Composable
fun topBar(navController: NavController){
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.10f)
            .background(colorResource(id = R.color.Secundario)),
        contentAlignment = Alignment.Center,
    ){
        Row(
           verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(text = "Título",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(.70f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun body(){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(.9f)
            .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 20.dp)
            .background(colorResource(id = R.color.Secundario))
    ){
        var title by remember { mutableStateOf("")}
        var description by remember { mutableStateOf("")}

        Column(
            modifier = Modifier
                //.align(Alignment.Center)
                .fillMaxWidth(1f)
                .padding(4.dp)
                .background(colorResource(id = R.color.Secundario)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // CAJA DE TEXTO DE TITULO
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                placeholder = { Text("Añadir título") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.Terciario),
                    focusedLabelColor = colorResource(id = R.color.Primario),
                    focusedIndicatorColor = colorResource(id = R.color.Primario)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // COMBOBOX PARA ESCOGER NOTA O TAREA
            val options = listOf("Nota","Tarea")
            ComboBox(options, "Asunto")
            Spacer(modifier = Modifier.height(16.dp))

            // DATETIMEPICKER PARA SELECCIONAR LA FECHA DE LA NOTA
            DatePicker()
            Spacer(modifier = Modifier.height(16.dp))

            // BOTONES DE MULTIMEDIA
            Column (
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.Terciario),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crea y/o selecciona archivos multimedia",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Row {
                    // BOTON DE GALERIA
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Primario)
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.galery),
                            contentDescription = "Galería",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // BOTON DE GRABADORA
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Primario)
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.microphone),
                            contentDescription = "Micrófono",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // BOTON DE OPCIONES
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Primario)
                        )
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // CAJA DE TEXTO PARA LA DESCRIPCI[ON
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = { Text("Añadir Descripción") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.Terciario),
                    focusedLabelColor = colorResource(id = R.color.Primario),
                    focusedIndicatorColor = colorResource(id = R.color.Primario)
                )
            )
            Spacer(modifier = Modifier.height(120.dp))

            // BOTÓN GUARDAR Y CANCELAR
            Row {
                // BOTON DE GUARDAR
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Primario)
                    )
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Guardar",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Guardar")
                }
                Spacer(modifier = Modifier.width(10.dp))
                // BOTON DE CANCELAR
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Primario)
                    )
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Cancelar",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Cancelar")
                }
            }

        }
    }
}

@Composable
fun footer(){
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .fillMaxSize()
            .background(colorResource(id = R.color.Primario))
    ){

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
        label = { Text("Fecha") },
        placeholder = { Text("Selecciona una fecha") },
        leadingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.clickable { nDatePickerDialog.show() }
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(id = R.color.Terciario),
            focusedLabelColor = colorResource(id = R.color.Primario),
            focusedIndicatorColor = colorResource(id = R.color.Primario)
        )
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
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.Terciario),
                focusedLabelColor = colorResource(id = R.color.Primario),
                focusedIndicatorColor = colorResource(id = R.color.Primario)
            )
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
fun AddEditNoteScreen(navController: NavController){
    Scaffold {
        BodyContentAddEditNote(navController)
    }
}
