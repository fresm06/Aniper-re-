package com.aniper.app.wallpaper.ai

import com.aniper.app.domain.model.Motion
import kotlin.random.Random

class CharacterBehaviorAI(
    val screenWidth: Int,
    val characterWidth: Int = 100
) {
    enum class State {
        IDLE,
        WALK_LEFT,
        WALK_RIGHT,
        GRABBED,
        FALLING
    }

    companion object {
        private const val WALK_SPEED = 3f // pixels per frame
        private const val STATE_CHANGE_INTERVAL = 3000L // ms
    }

    private var currentState = State.IDLE
    private var stateChangeTime = System.currentTimeMillis()
    private var currentX = screenWidth / 2f
    private var stateTimeDuration = (3..5).random() * 1000L

    val motionType: Motion
        get() = when (currentState) {
            State.IDLE -> Motion.IDLE
            State.WALK_LEFT -> Motion.WALK_LEFT
            State.WALK_RIGHT -> Motion.WALK_RIGHT
            State.GRABBED -> Motion.GRAB
            State.FALLING -> Motion.FALL
        }

    fun update() {
        if (currentState == State.GRABBED || currentState == State.FALLING) {
            return // Don't auto-change behavior when grabbed or falling
        }

        val now = System.currentTimeMillis()
        if (now - stateChangeTime >= stateTimeDuration) {
            changeState()
            stateChangeTime = now
            stateTimeDuration = (2..4).random() * 1000L
        }

        // Update position for walk states
        when (currentState) {
            State.WALK_LEFT -> {
                currentX -= WALK_SPEED
                if (currentX < 0) changeState()
            }
            State.WALK_RIGHT -> {
                currentX += WALK_SPEED
                if (currentX > screenWidth - characterWidth) changeState()
            }
            else -> {} // No position change for other states
        }
    }

    fun setGrabbed(grabbed: Boolean) {
        currentState = if (grabbed) State.GRABBED else State.IDLE
    }

    fun setFalling() {
        currentState = State.FALLING
    }

    fun setPosition(x: Float) {
        currentX = x.coerceIn(0f, (screenWidth - characterWidth).toFloat())
    }

    fun getPosition(): Float = currentX

    private fun changeState() {
        currentState = when (Random.nextInt(3)) {
            0 -> State.IDLE
            1 -> State.WALK_LEFT
            else -> State.WALK_RIGHT
        }
    }
}
