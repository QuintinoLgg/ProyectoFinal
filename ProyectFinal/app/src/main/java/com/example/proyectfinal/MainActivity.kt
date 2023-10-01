package com.example.proyectfinal

import android.app.Notification.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectfinal.ui.theme.ProyectFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
<<<<<<< HEAD
            //desing()
            design()
=======
            desing()
>>>>>>> 5c9fd017fe8326a5552d5649c8d7d214d9228813
        }
    }
}

//Funcion para ordenar el diseño, SOLAMENTE tiene esa funcionalidad
@Composable
fun desing(){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(colorResource(id = R.color.Secundario))
        ){
            TopBar()
            Body()
            Footer()
        }

        Box(
<<<<<<< HEAD
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 38.dp, end = 17.dp)
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.Terciario),
                        shape = RoundedCornerShape(25.dp)
                    )
=======
            modifier = Modifier.fillMaxSize()
            .padding(bottom = 38.dp, end = 17.dp)
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .background(colorResource(id = R.color.Terciario),
                        shape = RoundedCornerShape(25.dp) )
>>>>>>> 5c9fd017fe8326a5552d5649c8d7d214d9228813
                    .align(Alignment.BottomEnd)
            ) {
                Icon(painter = painterResource(id = R.drawable.more),
                    contentDescription = "Nuevo asunto")
            }
        }
    }
}

@Composable
fun TopBar(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.30f)

    ){
        //Variables
        var LookFor by remember{ mutableStateOf("") }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        ){
            //Fila del menu y el texto AGENDA
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ){
                IconButton(
                    onClick = { /*TODO*/ },
                    Modifier
                        .paint(painterResource(id = R.drawable.menu))
                        .padding(start = 40.dp)
                )
                {

                }

                Text(
                    text = "AGENDA",
                    fontSize = 34.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 60.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(.3f)
                    .padding(start = 45.dp, top = 20.dp)
            ){
                Row(
                    modifier = Modifier
                        .padding(start = 45.dp)
                        .background(Color.White)
                ){
                    IconButton(
                        onClick = { /*TODO*/ },
                        Modifier
                            .paint(painterResource(id = R.drawable.lookfor))
                            .fillMaxHeight()
                            .fillMaxWidth(.1f)

                    )
                    {

                    }
                    BasicTextField(
                        value = LookFor,
                        onValueChange = {
                            LookFor = it
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(1f)
                            .padding(start = 6.dp, top = 6.dp)
                    ) {
                        //CONDICION LookFor: si esta vacia mostrar el palceholder sino cambiar por lo que escriba el ususario
                        if (LookFor.isEmpty()) {
                            Text(
                                text = "Buscar",
                                fontSize = 18.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .fillMaxSize()
                            )
                        }else{
                            Text(
                                text = LookFor,
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }

        }


    }
}

//Cuerpo del diseño
@Composable
fun Body(){
    //Variables
    var Name by remember{ mutableStateOf("") }
    var Affair by remember{ mutableStateOf("") }
    var dateElement by remember{ mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.9f)
            .padding(start = 50.dp)

    ){
        Column {
            //Fila para los destacados con su linea vertical
            Row {
                Text(
                    text = "Destacados",
                    color = Color.White,
                    fontSize = 17.sp
                )
                VerticalLine()
            }

            Spacer(modifier = Modifier.height(30.dp))

            //Fila para los elementos con su linea vertical
            Row {
                Text(
                    text = "Elementos",
                    color = Color.White,
                    fontSize = 17.sp
                )
                VerticalLine()
            }

            Spacer(modifier = Modifier.height(30.dp))

            //Notas o tareas a mostrar
            Box(
                modifier = Modifier
<<<<<<< HEAD
                    .background(
                        colorResource(id = R.color.Terciario),
                        shape = RoundedCornerShape(15.dp)
                    )
=======
                    .background(colorResource(id = R.color.Terciario),
                        shape = RoundedCornerShape(15.dp))
>>>>>>> 5c9fd017fe8326a5552d5649c8d7d214d9228813
                    .fillMaxHeight(.18f)
                    .fillMaxWidth(.85f)
            ){
                Row {
                    //Nombre, asunto y fecha de la nota o tarea
                    Column {
                        Text(
                            text = "Nombre",
                            color = Color.Black,
                            fontSize = 19.sp,
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .padding(start = 12.dp)
                        )
                        Row {
                            Image(painter = painterResource(id = R.drawable.information),
                                contentDescription = "Informativo",
                                modifier = Modifier.padding(start = 12.dp))
                            Text(
                                text = "Asunto",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .fillMaxWidth(.8f)
                            )
                        }
                        Row {
                            Image(painter = painterResource(id = R.drawable.informativedate),
                                contentDescription = "Informativo fecha",
                                modifier = Modifier.padding(start = 12.dp))
                            Text(
                                text = "Fecha",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .fillMaxWidth(.8f)
                            )
                        }
                    }

                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.size(60.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.points),
                            contentDescription = "Menu de notas y tareas",
                            modifier = Modifier.padding(top = 18.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun Footer(){
    //Variables
    var Date by remember{ mutableStateOf("") }

    Box (
        modifier = Modifier
            .background(colorResource(id = R.color.Primario))
            .border(1.dp, Color.Black)
            .fillMaxSize()
    ){
        TextButton(onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxSize(.96f)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxSize()
            ){
                Image(painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Fecha")

                Text(text = "Fecha",
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(.70f)
                )


                Image(painter = painterResource(id = R.drawable.fechadesgloce),
                    contentDescription = "Fecha")
            }
        }
    }
}


//Funcion solo usada para ver la preview, sin uso mayor.
// Solo llamar la funcion que contiene el diseño
@Preview
@Composable
fun view(){
    desing()
}

//Para crear las lineas verticales
@Composable
fun VerticalLine() {
    Spacer(
        modifier = Modifier
            .height(2.dp)
            .width(200.dp)
            .background(Color.White)
    )
}