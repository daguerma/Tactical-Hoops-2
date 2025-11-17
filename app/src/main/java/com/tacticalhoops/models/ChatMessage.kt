package com.tacticalhoops.models

data class ChatMessage(
    val text: String,
    val play: Play? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val sender: String = "User"
)
