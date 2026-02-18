package com.aniper.app.wallpaper.engine

import android.graphics.Canvas
import android.view.Choreographer
import android.view.SurfaceHolder
import com.aniper.app.domain.model.Character
import com.aniper.app.domain.model.Motion
import com.aniper.app.wallpaper.ai.CharacterBehaviorAI
import com.aniper.app.wallpaper.physics.FallPhysics
import java.util.concurrent.CountDownLatch

class WallpaperEngine(
    private val surfaceHolder: SurfaceHolder,
    private val screenWidth: Int,
    private val screenHeight: Int
) {
    private var isRunning = false
    private var renderThread: Thread? = null
    private var choreographer: Choreographer? = null
    private var lastFrameTime = 0L

    private val behaviorAI = CharacterBehaviorAI(screenWidth)
    private val physics = FallPhysics(screenHeight)
    private val touchHandler = TouchHandler()
    private val renderer = CharacterRenderer()

    private var currentCharacter: Character? = null
    private var motionStartTime = 0L
    private var currentMotion = Motion.IDLE

    companion object {
        private const val TARGET_FPS = 60
        private const val FRAME_TIME_NS = 1_000_000_000L / TARGET_FPS
    }

    fun start() {
        if (isRunning) return
        isRunning = true
        renderThread = Thread(::renderLoop).apply {
            name = "WallpaperRenderThread"
            start()
        }
    }

    fun stop() {
        isRunning = false
        renderThread?.join(1000)
        renderThread = null
    }

    fun setCharacter(character: Character) {
        currentCharacter = character
        motionStartTime = System.currentTimeMillis()
    }

    fun onTouchEvent(x: Float, y: Float, motionEvent: android.view.MotionEvent) {
        // Handle touch events and update behavior accordingly
    }

    private fun renderLoop() {
        val choreographerLatch = CountDownLatch(1)

        val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())
        mainHandler.post {
            choreographer = Choreographer.getInstance()
            choreographerLatch.countDown()
        }

        choreographerLatch.await()

        val frameCallback = object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                if (isRunning) {
                    update()
                    render()
                    choreographer?.postFrameCallback(this)
                }
            }
        }

        mainHandler.post {
            choreographer?.postFrameCallback(frameCallback)
        }
    }

    private fun update() {
        // Update AI behavior
        behaviorAI.update()

        // Update physics
        physics.update()

        // Update current motion based on AI state and physics
        val newMotion = when {
            physics.isLanding && currentMotion == Motion.FALL -> Motion.LAND
            behaviorAI.motionType == Motion.WALK_LEFT -> Motion.WALK_LEFT
            behaviorAI.motionType == Motion.WALK_RIGHT -> Motion.WALK_RIGHT
            behaviorAI.motionType == Motion.IDLE -> Motion.IDLE
            else -> currentMotion
        }

        if (newMotion != currentMotion) {
            currentMotion = newMotion
            motionStartTime = System.currentTimeMillis()
        }
    }

    private fun render() {
        val canvas = try {
            surfaceHolder.lockCanvas()
        } catch (e: Exception) {
            return
        } ?: return

        try {
            // Draw background (transparent for wallpaper)
            canvas.drawARGB(0, 0, 0, 0)

            currentCharacter?.let { character ->
                val elapsedMs = System.currentTimeMillis() - motionStartTime
                val drawable = renderer.getFrameForMotion(currentMotion, elapsedMs)

                val x = behaviorAI.getPosition()
                val y = physics.currentY

                renderer.renderCharacter(canvas, x, y, drawable)
            }
        } finally {
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}
