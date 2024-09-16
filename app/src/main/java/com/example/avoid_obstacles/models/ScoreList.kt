package com.example.avoid_obstacles.models

data class ScoreList(
    var scoresArrayList: ArrayList<Score> = ArrayList()
) {

    fun addScore(score: Score): ScoreList {
        scoresArrayList.add(score)
        return this
    }
}

