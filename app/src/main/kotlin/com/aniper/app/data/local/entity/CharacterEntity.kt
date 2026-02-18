package com.aniper.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aniper.app.data.local.db.converter.MotionMapConverter
import com.aniper.app.data.local.db.converter.StringListConverter
import com.aniper.app.domain.model.CharacterStatus

@Entity(tableName = "characters")
@TypeConverters(MotionMapConverter::class, StringListConverter::class)
data class CharacterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val author: String,
    @ColumnInfo(name = "is_official")
    val isOfficial: Boolean = false,
    val tags: List<String> = emptyList(),
    val motions: Map<String, String> = emptyMap(), // Motion name -> URL/path
    @ColumnInfo(name = "download_count")
    val downloadCount: Int = 0,
    @ColumnInfo(name = "is_approved")
    val isApproved: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = CharacterStatus.INSTALLED.name
)
