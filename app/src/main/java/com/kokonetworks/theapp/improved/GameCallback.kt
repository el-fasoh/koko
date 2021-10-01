package com.kokonetworks.theapp.improved

interface GameCallback {
    fun onGameEnded(score: Int)

    fun onLevelChange(level: Int)
}