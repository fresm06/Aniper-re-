package com.aniper.app.wallpaper

import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.aniper.app.data.local.prefs.AppPreferences
import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.wallpaper.engine.WallpaperEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AnIperWallpaperService : WallpaperService() {

    @Inject
    lateinit var characterRepository: CharacterRepository

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreateEngine(): Engine {
        return AnIperEngine()
    }

    inner class AnIperEngine : Engine() {
        private var wallpaperEngine: WallpaperEngine? = null
        private var surfaceWidth = 0
        private var surfaceHeight = 0

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            setTouchEventsEnabled(true)

            // Initialize engine when surface is created
            GlobalScope.launch(Dispatchers.Main) {
                // Wait for surface to be ready
                surfaceHolder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        initializeWallpaperEngine()
                        observeActiveCharacter()
                    }

                    override fun surfaceChanged(
                        holder: SurfaceHolder,
                        format: Int,
                        width: Int,
                        height: Int
                    ) {
                        surfaceWidth = width
                        surfaceHeight = height
                        wallpaperEngine = WallpaperEngine(holder, width, height)
                        wallpaperEngine?.start()
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        wallpaperEngine?.stop()
                        wallpaperEngine = null
                    }
                })
            }
        }

        override fun onTouchEvent(event: MotionEvent) {
            wallpaperEngine?.onTouchEvent(event.x, event.y, event)
            super.onTouchEvent(event)
        }

        override fun onDestroy() {
            wallpaperEngine?.stop()
            super.onDestroy()
        }

        private fun initializeWallpaperEngine() {
            GlobalScope.launch(Dispatchers.IO) {
                val dimensions = surfaceHolder.surfaceFrame
                surfaceWidth = dimensions.width()
                surfaceHeight = dimensions.height()
            }
        }

        private fun observeActiveCharacter() {
            GlobalScope.launch(Dispatchers.Main) {
                appPreferences.activeCharacterId.collectLatest { characterId ->
                    if (characterId != null) {
                        val character = characterRepository.getCharacterById(characterId)
                        character?.let {
                            wallpaperEngine?.setCharacter(it)
                        }
                    }
                }
            }
        }
    }
}
