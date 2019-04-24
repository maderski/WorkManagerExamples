package io.maderski.workmanagerexamples.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.maderski.workmanagerexamples.clients.SlackClient
import io.maderski.workmanagerexamples.utils.ConfigUtils

const val SLACK_MESSAGE_ARG = "SLACK_MESSAGE"

class SlackPostOnceWorker(context: Context,
                          workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val slackToken = ConfigUtils.getValue(context.resources, ConfigUtils.SLACK_API_TOKEN_KEY)
    override fun doWork(): Result {
        val message = inputData.getString(SLACK_MESSAGE_ARG)
        return if (slackToken != null && message != null) {
            val slackClient = SlackClient(slackToken)
            val channelId = "play-ground"
            slackClient.connect()
            slackClient.postMessage(channelId, message)
            Result.success()
        } else {
            Result.failure()
        }

    }


}