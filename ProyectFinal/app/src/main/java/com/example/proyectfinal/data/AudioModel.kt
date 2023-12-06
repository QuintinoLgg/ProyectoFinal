package com.example.proyectfinal.data

import java.io.File

data class AudioModel(
    val audioFile: File,
    val isRecording: Boolean = false,
    val isPlaying: Boolean = false
)
