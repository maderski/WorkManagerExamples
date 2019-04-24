package io.maderski.workmanagerexamples.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import io.maderski.workmanagerexamples.workers.SLACK_MESSAGE_ARG
import io.maderski.workmanagerexamples.workers.SlackPostOnceWorker

class MainViewModel(val lifecycleOwner: LifecycleOwner) : ViewModel() {
    fun performSlackWork(): Operation {
        // create data that contains the first message
        val dataBuilder = Data.Builder()
        val inputData = dataBuilder.putString(SLACK_MESSAGE_ARG, "First slack message").build()
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
        return WorkManager.getInstance().enqueue(slackWorkRequest)
    }

    fun performCompressWork() {

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
    }
}