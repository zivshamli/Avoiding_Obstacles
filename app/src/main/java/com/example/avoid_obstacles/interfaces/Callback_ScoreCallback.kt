package com.example.avoid_obstacles.interfaces

import com.example.avoid_obstacles.models.Score

interface Callback_ScoreCallback {
    fun scoreClicked(score: Score, position: Int)
}