package com.aniper.app.wallpaper.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import com.aniper.app.domain.model.Motion

class CharacterRenderer(
    private val characterWidth: Int = 100,
    private val characterHeight: Int = 100
) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var currentFrameIndex = 0
    private val frameTimes = mutableMapOf<Motion, List<Int>>()
    private val frameImages = mutableMapOf<Motion, List<Drawable?>>()

    fun setMotionFrames(motion: Motion, drawables: List<Drawable?>, frameDelayMs: List<Int> = emptyList()) {
        frameImages[motion] = drawables
        frameTimes[motion] = if (frameDelayMs.isEmpty()) {
            List(drawables.size) { 66 } // Default 60fps ≈ 16ms per frame, use 66ms for smoother animation
        } else {
            frameDelayMs
        }
    }

    fun getFrameForMotion(motion: Motion, elapsedMs: Long): Drawable? {
        val drawables = frameImages[motion] ?: return null
        val delays = frameTimes[motion] ?: return drawables.firstOrNull()

        if (drawables.isEmpty()) return null

        var totalTime = 0L
        var frameIndex = 0

        for (i in delays.indices) {
            if (totalTime + delays[i] > elapsedMs) {
                frameIndex = i
                break
            }
            totalTime += delays[i].toLong()
            if (i == delays.size - 1) {
                frameIndex = i
            }
        }

        return drawables.getOrNull(frameIndex)
    }

    fun renderCharacter(
        canvas: Canvas,
        x: Float,
        y: Float,
        drawable: Drawable?
    ) {
        drawable?.let {
            it.setBounds(
                x.toInt(),
                y.toInt(),
                (x + characterWidth).toInt(),
                (y + characterHeight).toInt()
            )
            it.draw(canvas)
        }
    }

    fun renderBitmap(
        canvas: Canvas,
        x: Float,
        y: Float,
        bitmap: Bitmap?
    ) {
        bitmap?.let {
            canvas.drawBitmap(
                it,
                x,
                y,
                paint
            )
        }
    }
}
