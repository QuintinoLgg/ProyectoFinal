package com.example.proyectfinal.models
import androidx.room.TypeConverter

public class Converters {
    @TypeConverter
    fun fromStringList(stringList: List<String>?): String? {
        return stringList?.joinToString(separator = ",") { it.toString() }
    }

    @TypeConverter
    fun toStringList(stringListString: String?): List<String>? {
        return stringListString?.split(",")?.map { it.toString() }
    }
}