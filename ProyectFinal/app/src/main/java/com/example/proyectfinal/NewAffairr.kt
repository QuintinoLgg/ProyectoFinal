package com.example.proyectfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview

class NewAffairr : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

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
    }
}

@Composable
fun TopBar(){

}

@Composable
fun Body(){

}

@Composable
fun Footer(){

}


//Funcion solo usada para ver la preview, sin uso mayor.
// Solo llamar la funcion que contiene el diseño
@Preview
@Composable
fun view(){
    desing()
}