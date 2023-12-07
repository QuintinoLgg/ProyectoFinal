package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyectfinal.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.utils.NotesAppNavigationType
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.proyectfinal.data.AudioModel
import com.example.proyectfinal.ui.theme.AndroidAudioPlayer
import com.example.proyectfinal.ui.theme.AndroidAudioRecorder
import com.example.proyectfinal.ui.theme.ComposeFileProvider
import com.example.proyectfinal.ui.theme.GrabarAudioScreen
import java.io.File
import java.net.URI


//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun BodyContentAddNote(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
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
            var MyTitle = stringResource(id = R.string.agregar_nota)
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
    val currentTitulo = remember { mutableStateOf("") }
    val currentDescripcion = remember { mutableStateOf("") }
    val currentFoto = remember { mutableStateOf("") }
    val currentVideo = remember { mutableStateOf("") }


    // MULTIMEDIA NUEVA ------------------------------------------------
    val context = LocalContext.current
    var images by remember { mutableStateOf(listOf<String>()) }
    var uriCamara : Uri? = null

    val recorder by lazy {
        AndroidAudioRecorder(context)
    }

    val player by lazy {
        AndroidAudioPlayer(context)
    }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ){uri ->
        if(uri != null) {
            images = images.plus(uri!!.toString() + "|IMG")
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if(success){
                images = images.plus(uriCamara!!.toString()+"|IMG")
            }
        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if(success){
                images = images.plus(uriCamara!!.toString()+"|VID")
            }
        }
    )


    //VARIABLES DE AUDIO
    var i by remember {
        mutableStateOf(0)
    }


    var audioFiles by remember(i) {
        mutableStateOf(List(i) { index -> AudioModel(File(context.cacheDir, "audio_$index.mp3")) })
    }
    // MULTIMEDIA NUEVA ------------------------------------------------

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //item{
            // CAJA DE TEXTO DE TITULO
            TextField(
                value = currentTitulo.value,
                onValueChange = { value ->
                    currentTitulo.value = value
                },
                label = { Text(stringResource(id = R.string.titulo)) },
                placeholder = { Text(stringResource(id = R.string.agregar_titulo)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        //}

        /*
        item {
            // COMBOBOX PARA ESCOGER NOTA O TAREA
            val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
            ComboBox(options, stringResource(id = R.string.asunto), miViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }*/

        /*
        item {
            // DATETIMEPICKER PARA SELECCIONAR LA FECHA DE LA NOTA
            DatePicker(miViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }*/

        //item {
            // CAJA DE TEXTO PARA LA DESCRIPCI[ON
            TextField(
                value = currentDescripcion.value,
                onValueChange = { value ->
                    currentDescripcion.value = value
                },
                label = { Text(stringResource(id = R.string.descripcion)) },
                placeholder = { Text(stringResource(id = R.string.agregar_descripcion)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
        //}



        //item {
            // Multimedia(currentFoto, currentVideo)
            Row {
                // IMAGEN DE GALERIA
                IconButton(
                    onClick = {
                        getImageRequest.launch(arrayOf("image/*"))
                    }
                ) {
                    Icon(Icons.Filled.Filter, contentDescription = null)
                }
                // CAMARA
                IconButton(
                    onClick = {
                        uriCamara = ComposeFileProvider.getImageUri(context)
                        cameraLauncher.launch(uriCamara)
                    }
                ) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = null)
                }
                // VIDEO
                IconButton(
                    onClick = {
                        val uri = ComposeFileProvider.getImageUri(context)
                        videoLauncher.launch(uri)
                        uriCamara = uri
                    }
                ) {
                    Icon(Icons.Filled.Videocam, contentDescription = null)
                }
            }
        //}

        LazyColumn {
            itemsIndexed(images){index, uri ->
                ObjetoMultimedia(uri = uri, player = player)
            }

            item {
                // AUDIO
                val context = LocalContext.current
                val recorder by lazy {
                    AndroidAudioRecorder(context)
                }

                val player by lazy {
                    AndroidAudioPlayer(context)
                }



                Column {
                    audioFiles.forEachIndexed { index, audioModel ->
                        GrabarAudioScreen(
                            onClickStGra = {
                                val updatedAudioFiles = audioFiles.toMutableList()
                                updatedAudioFiles[index] = audioModel.copy(isRecording = true)
                                audioFiles = updatedAudioFiles

                                recorder.start(audioModel.audioFile)
                            },
                            onClickSpGra = {
                                val updatedAudioFiles = audioFiles.toMutableList()
                                updatedAudioFiles[index] = audioModel.copy(isRecording = false)
                                audioFiles = updatedAudioFiles

                                recorder.stop()
                            },
                            onClickStRe = {
                                val updatedAudioFiles = audioFiles.toMutableList()
                                updatedAudioFiles[index] = audioModel.copy(isPlaying = true)
                                audioFiles = updatedAudioFiles

                                player.start(audioModel.audioFile)
                            },
                            onClickSpRe = {
                                val updatedAudioFiles = audioFiles.toMutableList()
                                updatedAudioFiles[index] = audioModel.copy(isPlaying = false)
                                audioFiles = updatedAudioFiles

                                player.stop()
                            }
                        )
                    }

                    Button(onClick = { ++i }) {
                        Icon(
                            Icons.Filled.AddCircle,
                            contentDescription = "Más",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
        //item {
            // BOTÓN GUARDAR Y CANCELAR
            Row {
                //val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
                // BOTON DE GUARDAR
                Button(
                    onClick = {
                        viewModel.insertNote(
                            Note(
                                id = 0,
                                titulo = currentTitulo.value,
                                descripcion = currentDescripcion.value,
                                imageUri = currentFoto.value,
                                videoUri = currentVideo.value
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
                    onClick = {
                        audioFiles.forEach { it.audioFile.delete() }

                        // Reiniciar todos los datos al presionar el botón
                        i = 0
                        audioFiles = List(i) { index -> AudioModel(File(context.cacheDir, "audio_$index.mp3")) }
                        navController.popBackStack() },
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
           // }
        }

    }

}

@Composable
private fun ObjetoMultimedia(uri: String, player: AndroidAudioPlayer) {
    var _uri = uri.split("|")
    if(_uri.get(1).equals("IMG")){
        AsyncImage(
            model = Uri.parse(_uri.get(0)),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
    }
    else if(_uri.get(1).equals("VID")){
        VideoPlayer(videoUri = Uri.parse(_uri.get(0)), modifier = Modifier.size(50.dp))
    }
    Spacer(modifier = Modifier.height(20.dp))
    
    /*
    else if(_uri.get(1).equals("AUD")){
        IconButton(
            onClick = {
                val uriString = _uri.get(0)
                val __uri = URI(uriString)
                val file = File(uri)
                player.start(file)
            }
        ) {

        }
    }

     */
}


/*
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

 */

/*
// COMBOBOX PERSONALIZADO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(items: List<String>, etiqueta: String, miViewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = true,
            value = miViewModel.subject.value,
            onValueChange = {},
            label = { Text(etiqueta) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        miViewModel.subject.value = item
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNoteScreen(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentAddNote(viewModel, navController, navigationType)
    }
}

