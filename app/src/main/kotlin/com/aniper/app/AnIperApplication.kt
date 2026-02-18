package com.aniper.app

import android.app.Application
import com.aniper.app.data.local.db.AnIperDatabase
import com.aniper.app.data.local.entity.CharacterEntity
import com.aniper.app.domain.model.Motion
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class AnIperApplication : Application() {
    private val applicationScope = MainScope()

    override fun onCreate() {
        super.onCreate()
        initializeDefaultCharacters()
    }

    private fun initializeDefaultCharacters() {
        applicationScope.launch(Dispatchers.IO) {
            val database = AnIperDatabase.getInstance(this@AnIperApplication)
            val characterCount = database.characterDao().getCount()

            // Only insert default characters on first run
            if (characterCount == 0) {
                val defaultCharacters = listOf(
                    createFluffyCharacter(),
                    createSparkyCharacter()
                )
                database.characterDao().insertAll(defaultCharacters)
            }
        }
    }

    private fun createFluffyCharacter(): CharacterEntity {
        return CharacterEntity(
            id = "default_fluffy",
            name = "Fluffy",
            description = "A soft and cuddly white character who loves to bounce around",
            author = "공식",
            isOfficial = true,
            tags = listOf("cute", "white", "fluffy"),
            motions = mapOf(
                Motion.IDLE.name to "asset://fluffy_idle",
                Motion.WALK_LEFT.name to "asset://fluffy_walk_left",
                Motion.WALK_RIGHT.name to "asset://fluffy_walk_right",
                Motion.CLICK.name to "asset://fluffy_click",
                Motion.GRAB.name to "asset://fluffy_grab",
                Motion.FALL.name to "asset://fluffy_fall",
                Motion.LAND.name to "asset://fluffy_land"
            ),
            downloadCount = 10000,
            isApproved = true,
            createdAt = System.currentTimeMillis(),
            status = "INSTALLED"
        )
    }

    private fun createSparkyCharacter(): CharacterEntity {
        return CharacterEntity(
            id = "default_sparky",
            name = "Sparky",
            description = "A bright yellow star character that sparkles with magic",
            author = "공식",
            isOfficial = true,
            tags = listOf("star", "yellow", "sparkle"),
            motions = mapOf(
                Motion.IDLE.name to "asset://sparky_idle",
                Motion.WALK_LEFT.name to "asset://sparky_walk_left",
                Motion.WALK_RIGHT.name to "asset://sparky_walk_right",
                Motion.CLICK.name to "asset://sparky_click",
                Motion.GRAB.name to "asset://sparky_grab",
                Motion.FALL.name to "asset://sparky_fall",
                Motion.LAND.name to "asset://sparky_land"
            ),
            downloadCount = 8500,
            isApproved = true,
            createdAt = System.currentTimeMillis(),
            status = "INSTALLED"
        )
    }
}
