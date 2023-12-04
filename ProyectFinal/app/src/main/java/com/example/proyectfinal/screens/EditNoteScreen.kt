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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
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

    val currentTitulo = remember { mutableStateOf("") }
    val currentDescripcion = remember { mutableStateOf("") }
    val currentFoto = remember { mutableStateOf("") }
    val currentVideo = remember { mutableStateOf("") }
    val note = Constants.General.nota

    LaunchedEffect(true){
        scope.launch(Dispatchers.IO){
            currentTitulo.value = note.titulo
            currentDescripcion.value = note.descripcion
            currentFoto.value = note.imageUri
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
                placeholder = { Text(stringResource(id = R.string.agregar_titulo)) },
                modifier = Modifier.fillMaxWidth()
            )
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
                placeholder = { Text(stringResource(id = R.string.agregar_descripcion)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Multimedia(currentFoto, currentVideo)
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

            var audioFile: File? = null

            GrabarAudioScreen(
                onClickStGra = {
                    File(context.cacheDir, "audio.mp3").also {
                        recorder.start(it)
                        audioFile = it
                    }
                },
                onClickSpGra = { recorder.stop() },
                onClickStRe = { audioFile?.let { player.start(it) } },
                onClickSpRe = { player.stop() }
            )
        }

        item{
            // Mostrar la imagen seleccionada
            if (currentFoto.value.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(data = currentFoto.value),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp)
                )
            }
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
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(200.dp),
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

