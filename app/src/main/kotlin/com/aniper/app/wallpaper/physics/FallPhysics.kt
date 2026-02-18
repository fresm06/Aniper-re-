package com.aniper.app.wallpaper.physics

import kotlin.math.min

class FallPhysics(
    val screenHeight: Int,
    val characterHeight: Int = 100
) {
    companion object {
        private const val GRAVITY = 0.5f // pixels per frame^2
        private const val BOUNCE_DAMPING = 0.4f // reduce velocity after bounce
        private const val MIN_VELOCITY_TO_BOUNCE = 2f
    }

    data class PhysicsState(
        val y: Float = 0f,
        val velocity: Float = 0f,
        val isLanding: Boolean = false
    )

    private var state = PhysicsState()

    val currentY: Float get() = state.y
    val currentVelocity: Float get() = state.velocity
    val isOnGround: Boolean get() = state.y >= (screenHeight - characterHeight).toFloat()
    val isLanding: Boolean get() = state.isLanding

    fun startFalling(fromY: Float = 0f) {
        state = PhysicsState(y = fromY, velocity = 0f, isLanding = false)
    }

    fun update() {
        if (isOnGround) return

        // Apply gravity
        val newVelocity = state.velocity + GRAVITY
        val newY = state.y + newVelocity

        // Check ground collision
        val groundLevel = (screenHeight - characterHeight).toFloat()
        if (newY >= groundLevel) {
            // Hit the ground
            state = if (state.velocity > MIN_VELOCITY_TO_BOUNCE) {
                // Bounce with damping
                PhysicsState(
                    y = groundLevel,
                    velocity = -(newVelocity * BOUNCE_DAMPING),
                    isLanding = true
                )
            } else {
                // Stop bouncing
                PhysicsState(y = groundLevel, velocity = 0f, isLanding = true)
            }
        } else {
            state = state.copy(y = newY, velocity = newVelocity, isLanding = false)
        }
    }

    fun reset() {
        state = PhysicsState()
    }

    fun setPosition(y: Float) {
        state = state.copy(y = min(y, (screenHeight - characterHeight).toFloat()))
    }
}
