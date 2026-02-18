package com.aniper.app.wallpaper.engine

import android.view.MotionEvent

class TouchHandler {
    enum class TouchType {
        TAP,           // < 200ms
        LONG_PRESS,    // >= 200ms
        DRAG           // Multiple points with >= 200ms
    }

    data class TouchEvent(
        val type: TouchType,
        val x: Float,
        val y: Float,
        val deltaX: Float = 0f,
        val deltaY: Float = 0f
    )

    companion object {
        private const val LONG_PRESS_THRESHOLD = 200L // ms
        private const val DRAG_THRESHOLD = 10f // pixels
    }

    private var touchStartTime = 0L
    private var touchStartX = 0f
    private var touchStartY = 0f
    private var isDragging = false
    private var isLongPress = false

    fun handleMotionEvent(event: MotionEvent): TouchEvent? {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStartTime = System.currentTimeMillis()
                touchStartX = event.x
                touchStartY = event.y
                isDragging = false
                isLongPress = false
                null // Don't emit event on down yet
            }

            MotionEvent.ACTION_MOVE -> {
                val elapsedTime = System.currentTimeMillis() - touchStartTime
                val deltaX = event.x - touchStartX
                val deltaY = event.y - touchStartY
                val distance = kotlin.math.sqrt(deltaX * deltaX + deltaY * deltaY)

                if (!isDragging && distance > DRAG_THRESHOLD && elapsedTime >= LONG_PRESS_THRESHOLD) {
                    isDragging = true
                    isLongPress = true
                }

                if (isDragging) {
                    TouchEvent(
                        type = TouchType.DRAG,
                        x = event.x,
                        y = event.y,
                        deltaX = deltaX,
                        deltaY = deltaY
                    )
                } else {
                    null
                }
            }

            MotionEvent.ACTION_UP -> {
                val elapsedTime = System.currentTimeMillis() - touchStartTime
                val deltaX = event.x - touchStartX
                val deltaY = event.y - touchStartY
                val distance = kotlin.math.sqrt(deltaX * deltaX + deltaY * deltaY)

                val touchEvent = when {
                    isDragging -> {
                        // Already handled as drag, just finish
                        TouchEvent(
                            type = TouchType.DRAG,
                            x = event.x,
                            y = event.y,
                            deltaX = deltaX,
                            deltaY = deltaY
                        )
                    }
                    elapsedTime >= LONG_PRESS_THRESHOLD -> {
                        TouchEvent(
                            type = TouchType.LONG_PRESS,
                            x = event.x,
                            y = event.y
                        )
                    }
                    else -> {
                        TouchEvent(
                            type = TouchType.TAP,
                            x = event.x,
                            y = event.y
                        )
                    }
                }

                isDragging = false
                isLongPress = false
                touchEvent
            }

            else -> null
        }
    }

    fun isCurrentlyTouching(): Boolean = isDragging || isLongPress
}
