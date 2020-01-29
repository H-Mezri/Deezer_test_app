package com.mezri.deezertestapp.data.errors

/**
 * App information contain the message ID resource and the throwable if exist
 */
data class AppInformation(
    val messageId: Int,
    val throwable : Throwable? = null
)
