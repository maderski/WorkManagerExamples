package io.maderski.workmanagerexamples.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.maderski.workmanagerexamples.helpers.NotificationHelper

const val NOTIFICATION_MESSAGE_ARG = "NOTIFICATION_MESSAGE"

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val notificationHelper = NotificationHelper(context)
    override fun doWork(): Result {
        val title = "Chain Demo"
        val message = inputData.getString(NOTIFICATION_MESSAGE_ARG)
        return if (message != null) {
            notificationHelper.postNotification(title, message)
            Result.success()
        } else {
            Result.failure()
        }
    }
}