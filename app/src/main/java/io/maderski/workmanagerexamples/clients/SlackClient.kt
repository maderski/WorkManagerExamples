package io.maderski.workmanagerexamples.clients

import allbegray.slack.SlackClientFactory
import allbegray.slack.rtm.SlackRealTimeMessagingClient

class SlackClient(slackToken: String) : MessagingClient {
    private val webApiClient = SlackClientFactory.createWebApiClient(slackToken)
    private val webSocketUrl = webApiClient.startRealTimeMessagingApi().findPath("url").asText()
    private val rtmClient = SlackRealTimeMessagingClient(webSocketUrl, null)

    override fun connect() {
        rtmClient.connect()
    }

    override fun disconnect() {
        rtmClient.close()
    }

    override fun postMessage(channelId: String, message: String) {
        webApiClient.postMessage(channelId, message)
    }
}