package io.maderski.workmanagerexamples.viewmodels

import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import io.maderski.workmanagerexamples.workers.NOTIFICATION_MESSAGE_ARG
import io.maderski.workmanagerexamples.workers.NotificationWorker
import io.maderski.workmanagerexamples.workers.SLACK_MESSAGE_ARG
import io.maderski.workmanagerexamples.workers.SlackPostOnceWorker
import java.util.concurrent.TimeUnit

class MainViewModel(val lifecycleOwner: LifecycleOwner) : ViewModel() {
    private val dataBuilder = Data.Builder()
    fun performSlackWork() {
        val inputData = dataBuilder.putString(SLACK_MESSAGE_ARG, "I did some work!").build()
        // set Constraints
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        // create WorkRequest
        val slackWorkRequest = OneTimeWorkRequestBuilder<SlackPostOnceWorker>()
            .addTag(SLACK_WORK_TAG)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        // enquene work request
        WorkManager.getInstance().enqueue(slackWorkRequest)
    }

    fun cancelRunningWork() {
        val delayedWorkRequest = getOneTimeNotificationWorkRequest("I should of been cancelled", 30000L)
        WorkManager.getInstance().enqueue(delayedWorkRequest)
        Handler().postDelayed({
            WorkManager.getInstance().cancelAllWorkByTag(NOTIFICATION_WORK_TAG)
        }, 5000)
    }

    fun performDemoChainsWork() {
        val workA = getOneTimeNotificationWorkRequest("Kotlin rocks!")
        val workB = getOneTimeNotificationWorkRequest("WorkManger is awesome!", 5000L)
        val workC = getOneTimeNotificationWorkRequest("Chaining is easy!", 5000L)
        WorkManager.getInstance()
            .beginWith(workA)
            .then(workB)
            .then(workC)
            .enqueue()
    }

    private fun getOneTimeNotificationWorkRequest(message: String, delayInMillis: Long = 0L): OneTimeWorkRequest {
        val inputData = dataBuilder.putString(NOTIFICATION_MESSAGE_ARG, message).build()
        return OneTimeWorkRequestBuilder<NotificationWorker>()
            .addTag(NOTIFICATION_WORK_TAG)
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()
    }

    // observe work state of a Worker
    inline fun observeWorkFor(tag: String, crossinline task: (WorkInfo.State) -> Unit) {
        WorkManager.getInstance().getWorkInfosByTagLiveData(tag).observe(lifecycleOwner, Observer { workInfo ->
            workInfo?.let {
                task(it.first().state)
            }
        })
    }

    class ViewModelFactory(private val lifecycleOwner: LifecycleOwner): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(lifecycleOwner) as T
        }
    }

    companion object {
        const val SLACK_WORK_TAG = "SlackWorker"
        const val NOTIFICATION_WORK_TAG = "NotificationWorker"
    }
}