package com.kerite.pokedex.util

/**
 * AntiShaker
 *
 * @param interval The least time between two actions
 * @author Kerite
 */
class AntiShaker(
    private val interval: Long = DEFAULT_ANTI_SHAKE_INTERVAL
) {
    private var latestActionTime: Long = 0

    companion object {
        const val DEFAULT_ANTI_SHAKE_INTERVAL = 1000L
        val GlobalAntiShaker: AntiShaker = AntiShaker(DEFAULT_ANTI_SHAKE_INTERVAL)
    }

    fun antiShake(action: () -> Unit) {
        if (latestActionTime == 0L || System.currentTimeMillis() - latestActionTime >= interval) {
            latestActionTime = System.currentTimeMillis()
            action()
        }
    }

    @SuppressWarnings("unused")
    fun resetInterval() {
        latestActionTime = 0
    }
}