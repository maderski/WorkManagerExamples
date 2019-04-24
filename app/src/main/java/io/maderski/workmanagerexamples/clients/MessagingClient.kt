package io.maderski.workmanagerexamples.clients

interface MessagingClient {
    fun connect()
    fun disconnect()
    fun postMessage(channelId: String, message: String)
}