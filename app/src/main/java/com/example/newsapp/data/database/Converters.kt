package com.example.newsapp.data.database

import androidx.room.TypeConverter
import com.example.newsapp.data.database.model.SourceDbModel

class Converters {

    @TypeConverter
    fun fromSourceDbModel(source: SourceDbModel): String? {
        return source.name
    }

    @TypeConverter
    fun toSourceDbModel(name: String): SourceDbModel {
        return SourceDbModel(id = name, name = name)
    }
}