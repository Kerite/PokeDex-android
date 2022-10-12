package com.kerite.pokedex.work

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kerite.fission.android.extensions.shortToast
import com.kerite.pokedex.installApk
import java.io.File

class UpgradeWorker(
    private val appContext: Context, private val workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val downloadManager = appContext.getSystemService<DownloadManager>()
            ?: return Result.failure().also {
                appContext.shortToast("找不到系统下载管理器")
            }
        val requestedUrl = inputData.getString(INPUT_DATA_DOWNLOAD_URL)
            ?: return Result.failure()
        val version = inputData.getString(INPUT_DATA_VERSION_NAME)
            ?: return Result.failure()
        val fileUri = File(appContext.externalCacheDir, "update-apks/${version}.apk").toUri()

        val request = DownloadManager.Request(requestedUrl.toUri()).apply {
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            setTitle("Updating PokeDex")
            setDestinationUri(fileUri)
        }
        val downloadId = downloadManager.enqueue(request)
        applicationContext.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val cursor = downloadManager.query(
                    DownloadManager.Query().apply { setFilterById(downloadId) }
                )
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    when (cursor.getInt(columnIndex)) {
                        DownloadManager.STATUS_SUCCESSFUL -> {
                            appContext.installApk(fileUri)
                            cursor.close()
                            applicationContext.unregisterReceiver(this)
                        }
                    }
                }
            }
        }, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        return Result.success()
    }

    companion object {
        const val INPUT_DATA_DOWNLOAD_URL = "download_url"
        const val INPUT_DATA_VERSION_NAME = "version_name"
    }
}