package com.example.proyectfinal.screens
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import java.util.Calendar
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import androidx.core.app.AlarmManagerCompat
import androidx.navigation.NavController
import com.example.proyectfinal.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectfinal.data.AudioModel
import com.example.proyectfinal.ui.theme.MainViewModel
import com.example.proyectfinal.models.Task
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.theme.AndroidAudioPlayer
import com.example.proyectfinal.ui.theme.AndroidAudioRecorder
import com.example.proyectfinal.ui.theme.ComposeFileProvider
import com.example.proyectfinal.ui.theme.GrabarAudioScreen
import com.example.proyectfinal.ui.theme.NOTIFICATION_ID
import com.example.proyectfinal.ui.theme.NotificacionProgramada
import com.example.proyectfinal.ui.utils.NotesAppNavigationType
import java.io.File

//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyContentAddTask(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Content(viewModel, navController, navigationType)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    //Variable de ViewModel
    val miViewModel = viewModel<MainViewModel>()

    Scaffold (
        topBar = {
            var MyTitle = stringResource(id = R.string.agregar_tarea)
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


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun UI(viewModel: miViewModel, miViewModel: MainViewModel, navController: NavController){
    // VARIABLES
    val context = LocalContext.current                                // Variable que guarda el contexto
    val currentTitulo = remember { mutableStateOf("") }         // Variable que guarda el titulo
    val currentDescripcion = remember { mutableStateOf("") }    // Variable que guarda la descripcion
    val currentFecha = remember { mutableStateOf("") }          // Variable que guarda la fecha de la tarea
    var images by remember { mutableStateOf(listOf<String>()) }       // Variable que guarda las Uri's de las imagenes y videos
    var uriCamara : Uri? = null                                     // Variable que guarda la Uri de la foto que se tome con la camara

    val player by lazy {                // Variable usada para el reproductor de audio
        AndroidAudioPlayer(context)
    }

    // Variable que crea el canal de notificaciones
    val idCanal = com.example.proyectfinal.ui.utils.Constants.channelId

    // Variable de audio
    var i by remember {
        mutableStateOf(0)
    }

    // Variable que guarda los archivos de audio
    var audioFiles by remember(i) {
        mutableStateOf(List(i) { index -> AudioModel(File(context.cacheDir, "audio_$index.mp3")) })
    }

    // Creacion del canal de notificacion
    LaunchedEffect(Unit){
        crearCanalNotificacion(idCanal, context)
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


    //- --------------------------------------------------------------------------------------


    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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

        // DATETIMEPICKER PARA SELECCIONAR LA FECHA DE LA NOTA
        DatePicker(miViewModel, currentFecha)
        Spacer(modifier = Modifier.height(16.dp))

        HourPicker(miViewModel)
        Text(text = "Hora seleccionada: ${miViewModel.destinyHours.value}:${miViewModel.destinyMinutes.value}")
        Spacer(modifier = Modifier.height(20.dp))

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


        // BOTÓN GUARDAR Y CANCELAR
        Row {
            //val options = listOf(stringResource(id = R.string.nota), stringResource(id = R.string.tarea))
            // BOTON DE GUARDAR
            Button(
                onClick = {
                    viewModel.insertTask(
                        Task(
                            id=0,
                            titulo = currentTitulo.value,
                            descripcion = currentDescripcion.value, fecha = currentFecha.value,
                            images = images.joinToString()
                        )
                    )
                    val millis = conversion(miViewModel.destinyHours.value.toLong(), miViewModel.destinyMinutes.value.toLong())
                    notificacionesProgramadas(context, millis)
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
        }




        LazyColumn{
            itemsIndexed(images){index, uri ->
                ObjetoMultimedia(uri = uri, player = player)
            }

            item{
                // Audio
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


// Funcion que toma el tiempo actual
private fun getCurrentTime(): Pair<Int, Int>{
    val cal = Calendar.getInstance()
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    return Pair(hour,minute)
}


// Funcion que convierte una hora en formao HH:MM a milisegundos
// Tomando como punto de partida el punto actual
private fun conversion(hora: Long, minuto: Long): Long {
    val dupla = getCurrentTime()
    val millis = (((hora-dupla.first)*60) + (minuto-dupla.second)) * 60 * 1000
    return millis
}


// Composable que funciona como un DateTimePicker que toma la fecha en formato dd/MM/AAAA
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    miViewModel: MainViewModel,
    currentFecha: MutableState<String>
){
    var fecha by rememberSaveable {
        mutableStateOf("")
    }
    val year: Int
    var month: Int
    val day: Int
    val nCalendar = Calendar.getInstance()
    year = nCalendar.get(Calendar.YEAR)
    month = nCalendar.get(Calendar.MONTH)
    day = nCalendar.get(Calendar.DAY_OF_MONTH)

    val nDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year: Int, month: Int, day: Int ->
            val mes = month + 1
            fecha = "$day/$mes/$year"
            miViewModel.setdate(fecha) // Aquí establece la fecha en el ViewModel
            miViewModel.setdestinyYears(year.toLong())
            miViewModel.setdestinyMonths(month.toLong())
            miViewModel.setdestinyDays(day.toLong())
            currentFecha.value = fecha
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
        },
        modifier = Modifier.fillMaxWidth()
    )
}


// Composable que toma la hora en formato HH:MM
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourPicker(
    miViewModel: MainViewModel
){
    var selectedHour by remember {
        mutableIntStateOf(0)
    }

    var selectedMinute by remember {
        mutableIntStateOf(0)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )

    if(showDialog){
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            onDismissRequest = {
                showDialog = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TimePicker(state = timePickerState)
                Row {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }

                    TextButton(
                        onClick = {
                            showDialog = false
                            selectedHour = timePickerState.hour
                            selectedMinute = timePickerState.minute
                            miViewModel.setdestinyHours(selectedHour.toLong())
                            miViewModel.setdestinyMinutes(selectedMinute.toLong())
                        }
                    ) {
                        Text(text = "Confirmar")
                    }
                }
            }
        }
    }
    Button(
        onClick = {
            showDialog = true
        }
    ) {
        Text(text = "Seleccionar hora")
    }
}


//
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ComboBox(items: List<String>, etiqueta: String, miViewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
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
                        if(miViewModel.subject.value.equals(items[0])) miViewModel.milisegundos.value = 5000            // 5 SEGUNDOS
                        else if(miViewModel.subject.value.equals(items[1])) miViewModel.milisegundos.value = 10000       // 10 SEGUNDOS
                        else if(miViewModel.subject.value.equals(items[2])) miViewModel.milisegundos.value = 30000      // 30 SEGUNDOS
                        else if(miViewModel.subject.value.equals(items[3])) miViewModel.milisegundos.value = 60000     // 1 MINUTOS
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


private fun notificacionesProgramadas(
    context: Context,
    millis: Long
) {
    val intervalos = millis / 3
    val totalNotificaciones = 3
    val tiempoActual = System.currentTimeMillis()

    for (i in 1..totalNotificaciones) {
        val intent = Intent(context, NotificacionProgramada::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID + i, // Usar identificadores únicos para cada PendingIntent
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Programar una alarma con un intervalo entre notificaciones
        AlarmManagerCompat.setExact(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            tiempoActual + i * intervalos,
            pendingIntent
        )
    }
}

private fun crearCanalNotificacion(
    idCanal: String,
    context: Context
)
{
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val nombre = "CanalMascota"
        val descripcion = "Canal de notificaciones Mascota"
        val importancia = NotificationManager.IMPORTANCE_DEFAULT
        val canal = NotificationChannel(idCanal, nombre, importancia)
            .apply{
                description = descripcion
            }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationManager.createNotificationChannel(canal)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTaskScreen(viewModel: miViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Scaffold {
        BodyContentAddTask(viewModel, navController, navigationType)
    }
}



