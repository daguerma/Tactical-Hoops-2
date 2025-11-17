package com.tacticalhoops.models

data class Player(
    var x: Float,
    var y: Float,
    val team: Int, // 1 or 2
    val id: Int
)

data class Ball(
    var x: Float,
    var y: Float
)

data class Arrow(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val color: Int
)

data class Play(
    val players: MutableList<Player> = mutableListOf(),
    val balls: MutableList<Ball> = mutableListOf(),
    val arrows: MutableList<Arrow> = mutableListOf(),
    val timestamp: Long = System.currentTimeMillis()
)
