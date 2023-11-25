package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.proyectfinal.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.data.dataNotas
import com.example.proyectfinal.ui.theme.ComposeFileProvider
import com.example.proyectfinal.ui.utils.NotesAppNavigationType
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import coil.compose.AsyncImage

import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentAddEditNote(navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Content(navController, navigationType)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(navController: NavController, navigationType: NotesAppNavigationType){
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
                        UI(miViewModel, navController)
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
                UI(miViewModel, navController)
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
                UI(miViewModel, navController)
            }
        }
        // PARA PANTALLAS EXTENSAS, EL CONTENIDO SE INCLUYE CON EL NAVIGATION DRAWER

    }
}


@Composable
private fun UI(miViewModel: MainViewModel, navController: NavController){


    LazyColumn(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item{
            // CAJA DE TEXTO DE TITULO
            TextField(
                value = miViewModel.title.value,
                onValueChange = { newTitle ->
                    miViewModel.setTitle(newTitle)
                },
                label = { Text(stringResource(id = R.string.titulo)) },
                placeholder = { Text(stringResource(id = R.string.agregar_titulo)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            // COMBOBOX PARA ESCOGER NOTA O TAREA
            val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
            ComboBox(options, stringResource(id = R.string.asunto),miViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            // DATETIMEPICKER PARA SELECCIONAR LA FECHA DE LA NOTA
            DatePicker(miViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            MultiMedia()
        }

        item {
            // CAJA DE TEXTO PARA LA DESCRIPCI[ON
            TextField(
                value = miViewModel.description.value,
                onValueChange = { newDescription ->
                    miViewModel.setDescription(newDescription)
                },
                label = { Text(stringResource(id = R.string.descripcion)) },
                placeholder = { Text(stringResource(id = R.string.agregar_descripcion)) }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            // BOTÓN GUARDAR Y CANCELAR
            Row {
                val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
                // BOTON DE GUARDAR
                Button(
                    onClick = {
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




// DATETIME PICKER PERSONALIZADO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(miViewModel: MainViewModel){
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
        { _, year: Int, month: Int, day: Int ->
            fecha = "$day/$month/$year"
            miViewModel.setdate(fecha) // Aquí establece la fecha en el ViewModel
        },
        year, month, day
    )

    TextField(
        value = miViewModel.date.value,
        onValueChange = { newDate ->
            miViewModel.setdate(newDate)
        },
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
fun ComboBox(items: List<String>, etiqueta: String, miViewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = miViewModel.subject.value,
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
                        miViewModel.subject.value = item
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
        BodyContentAddEditNote(navController, navigationType)
    }
}




//Funcion para el apartado de multimedias
@Composable
fun MultiMedia(){

    //VARIABLES MULTIMEDIA
    // 1
    var hasImage by remember {
        mutableStateOf(false)
    }
    var hasVideo by remember {
        mutableStateOf(false)
    }
    // 2
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // TODO
            // 3
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            hasVideo = success
        }
    )

    val context = LocalContext.current

    //DISEÑO
    // BOTONES DE MULTIMEDIA
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 4
        if ((hasImage or hasVideo) && imageUri != null) {
            // 5
            if(hasImage){
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier.size(300.dp),
                    contentDescription = "Selected image",
                )
            }
            if(hasVideo) {VideoPlayer(videoUri = imageUri!!)}
        }

        Text(
            text = stringResource(id = R.string.apartado_multimedia),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
        Row {
            // BOTON DE GALERIA
            Button(
                onClick = { imagePicker.launch("image/*")  }
            ) {
                Icon(
                    Icons.Filled.Image,
                    contentDescription = "Galería",
                    modifier = Modifier.size(25.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            // BOTON DE FOTO

            Button(
                onClick = {
                    val uri = ComposeFileProvider.getImageUri(context)
                    imageUri = uri
                    cameraLauncher.launch(uri) },
            ) {
                Icon(
                    Icons.Filled.CameraAlt,
                    contentDescription = "Video",
                    modifier = Modifier.size(25.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            // BOTON DE VIDEO
            Button(
                onClick = {
                    val uri = ComposeFileProvider.getImageUri(context)
                    imageUri = uri
                    videoLauncher.launch(uri) },
            ) {
                Icon(
                    Icons.Default.VideoCall,
                    contentDescription = "Video",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}





@Composable
fun VideoPlayer(videoUri: Uri, modifier: Modifier = Modifier.fillMaxWidth()) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
        }
    }
    val playbackState = exoPlayer
    val isPlaying = playbackState?.isPlaying ?: false

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f).height(200.dp),
    )

    IconButton(
        onClick = {
            if (isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        },
        modifier = Modifier
            //.align(Alignment.BottomEnd)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Filled.Refresh else Icons.Filled.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
    }
}
