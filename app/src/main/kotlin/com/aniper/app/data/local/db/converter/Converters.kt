package com.aniper.app.data.local.db.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return if (value == null) emptyList() else Json.decodeFromString(value)
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return Json.encodeToString(list)
    }
}

class MotionMapConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String, String> {
        return if (value == null) emptyMap() else Json.decodeFromString(value)
    }

    @TypeConverter
    fun toString(map: Map<String, String>): String {
        return Json.encodeToString(map)
    }
}
