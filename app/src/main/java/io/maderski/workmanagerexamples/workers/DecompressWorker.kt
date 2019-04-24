package io.maderski.workmanagerexamples.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DecompressWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}