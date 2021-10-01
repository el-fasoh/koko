package com.kokonetworks.theapp.improved

import java.util.Timer
import kotlin.concurrent.fixedRateTimer

class ImprovedMole(private val improvedField: ImprovedField) {
    private var startTimeForLevel: Long = System.currentTimeMillis()
    private var currentLevel = 0
    private var timer: Timer? = null

    fun startHopping() {
        improvedField.callback?.onLevelChange(getCurrentLevel())
        startTimeForLevel = System.currentTimeMillis()
        timer?.cancel()
        timer = fixedRateTimer(name = "mole-timer", period = LEVELS[currentLevel]) {
            if (System.currentTimeMillis() - startTimeForLevel >= LEVEL_DURATION && getCurrentLevel() < LEVELS.size) {
                nextLevel()
                return@fixedRateTimer
            }
            improvedField.setActive(nextHole())
        }
    }

    fun getCurrentLevel() = currentLevel + 1

    private fun nextLevel() {
        currentLevel++
        timer?.cancel()
        startHopping()
    }

    fun stopHopping() {
        timer?.cancel()
    }

    private fun nextHole(): Int {
        val position = (0 until improvedField.getTotalChips()).random()
        if (position == improvedField.currentSelected) {
            return nextHole()
        }
        return position
    }

    companion object {
        private val LEVELS = arrayOf<Long>(1000, 900, 800, 700, 600, 500, 400, 300, 200, 100)
        private const val LEVEL_DURATION = 10_000
    }
}