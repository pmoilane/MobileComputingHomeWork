package com.example.mobilecomputinghw.util

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class NotificationWorker(
    context: Context,
    userParameters: WorkerParameters
) : Worker(context, userParameters) {

    override fun doWork(): Result {
        return try {
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}
