package com.example.proyectfinal.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.proyectfinal.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.proyectfinal.Constants
import com.example.proyectfinal.data.AudioModel
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.data.bottomNavItems
import com.example.proyectfinal.models.Note
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.theme.AndroidAudioPlayer
import com.example.proyectfinal.ui.theme.AndroidAudioRecorder
import com.example.proyectfinal.ui.theme.ComposeFileProvider
import com.example.proyectfinal.ui.theme.GrabarAudioScreen
import com.example.proyectfinal.ui.utils.NotesAppNavigationType
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

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
        else if(navigationType == NotesAppNavigationType.PERMANENT_NAVIGATION_DRAWER){
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


@Composable
private fun UI(viewModel: miViewModel, miViewModel: MainViewModel, navController: NavController){
    val scope = rememberCoroutineScope()
    val currentTitulo = remember { mutableStateOf("") }         // Variable que guarda el titulo
    val currentDescripcion = remember { mutableStateOf("") }    // Variable que guarda la descripcion
    var images by remember { mutableStateOf(listOf<String>()) }       // Variable que guarda las Uri's de las imagenes y videos
    val note = Constants.General.nota
    val context = LocalContext.current
    var uriCamara : Uri? = null         // Variable que guarda la Uri de la foto que se tome con la camara

    val player by lazy {
        AndroidAudioPlayer(context)
    }

    // Variable de audio
    var i by remember {
        mutableStateOf(0)
    }

    // Variable que guarda los archivos de audio
    var audioFiles by remember(i) {
        mutableStateOf(List(i) { index -> AudioModel(File(context.cacheDir, "audio_$index.mp3")) })
    }

    // Variable usada para tomar imagen de la galeria o documentos
    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ){uri ->
        if(uri != null) {
            images = images.plus(uri!!.toString() + "|IMG")
        }
    }

    // Variable usada para tomar una fotografia con la camara nativa del telefono
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if(success){
                images = images.plus(uriCamara!!.toString()+"|IMG")
            }
        }
    )

    // Variable usada para poder lanzar la grabadora de video
    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if(success){
                images = images.plus(uriCamara!!.toString()+"|VID")
            }
        }
    )


    LaunchedEffect(true){
        scope.launch(Dispatchers.IO){
            currentTitulo.value = note.titulo
            currentDescripcion.value = note.descripcion
            if(note.images!!.isNotBlank()){
                // Conversion de las uris
                val urisFromString = note.images!!.split(", ").map { it }
                images = urisFromString
            }
        }
    }



    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Campo de texto para el titulo
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

        // Campo de texto para la descripcion
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

        // Botones de multimedia
        Row {
            // Tomar imagen de galeria
            TextButton(
                onClick = {
                    getImageRequest.launch(arrayOf("image/*"))
                }
            ) {
                Icon(Icons.Default.Filter, contentDescription = null)
                Text("Galeria")
            }
            // Tomar imagen con la camara
            TextButton(
                onClick = {
                    uriCamara = ComposeFileProvider.getImageUri(context)
                    cameraLauncher.launch(uriCamara)
                }
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Text("Camara")
            }
            // Tomar video
            TextButton(
                onClick = {
                    val uri = ComposeFileProvider.getImageUri(context)
                    videoLauncher.launch(uri)
                    uriCamara = uri
                }
            ) {
                Icon(Icons.Default.Videocam, contentDescription = null)
                Text("Video")
            }

        }


        // Boton de guardar y cancelar
        Row {
            // Boton de guardar
            Button(
                onClick = {
                    viewModel.updateNote(
                        Note(
                            id = 0,
                            titulo = currentTitulo.value,
                            descripcion = currentDescripcion.value,
                            images = images.joinToString()
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
            // Boton de cancelar
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
        }


        // Lazy column para mostrar el contenido multimedia
        LazyColumn {
            itemsIndexed(images){index, uri ->
                ObjetoMultimedia(uri = uri, player = player)
            }

            item {
                // Audio
                val context = LocalContext.current
                val recorder by lazy {
                    AndroidAudioRecorder(context)
                }

                val player by lazy {
                    AndroidAudioPlayer(context)
                }

                // Mostrar los audios y sus botones
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
                    // Agregar nota de voz
                    TextButton(
                        onClick = {
                            ++i
                        }
                    ) {
                        Icon(Icons.Default.Mic, contentDescription = null)
                        Text("Audio")
                    }
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


// Composable de multimedia que ya no utilizamos
@Composable
fun Multimedia(
    rutaImagen: MutableState<String>,
    rutaVideo: MutableState<String>
){
    //VARIABLES
    // 1
    var hasImage by remember {
        mutableStateOf(
            if(rutaImagen.value == "") false
            else true
        )
    }
    var hasVideo by remember {
        mutableStateOf(
            if(rutaVideo.value == "") false
            else true
        )
    }
    // 2
    var imageUri by remember {
        mutableStateOf(rutaImagen.value)
    }

    var videoUri by remember {
        mutableStateOf(rutaVideo.value)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // TODO
            // 3
            hasImage = uri != null
            imageUri = uri.toString()
            rutaImagen.value = uri.toString()
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

        if(hasImage && imageUri!=""){
            AsyncImage(
                model = Uri.parse(imageUri),
                modifier = Modifier.size(300.dp),
                contentDescription = "Selected image",
            )
        }

        if(hasVideo && videoUri!="") {
            VideoPlayer(
                videoUri = Uri.parse(videoUri)
            )
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
                    imageUri = uri.toString()
                    rutaImagen.value = imageUri
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
                    videoUri = uri.toString()
                    rutaVideo.value = videoUri
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



