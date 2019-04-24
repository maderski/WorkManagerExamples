package io.maderski.workmanagerexamples.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.maderski.workmanagerexamples.clients.SlackClient
import io.maderski.workmanagerexamples.utils.ConfigUtils


class SlackPostOnceWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
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

    companion object {
        const val SLACK_MESSAGE_ARG = "arg_slack_message"
        const val SLACK_WORK_TAG = "SlackWorker"
    }

}