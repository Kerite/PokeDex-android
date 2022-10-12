package com.kerite.pokedex.intergrates

import android.app.Activity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.workDataOf
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kerite.fission.android.extensions.shortToast
import com.kerite.pokedex.R
import com.kerite.pokedex.work.UpgradeWorker
import com.microsoft.appcenter.distribute.DistributeListener
import com.microsoft.appcenter.distribute.ReleaseDetails
import timber.log.Timber

class PokeDexDistributeListener : DistributeListener {
    override fun onReleaseAvailable(activity: Activity?, releaseDetails: ReleaseDetails?): Boolean {
        Timber.tag("ReleaseStat").i("Update Available")
        releaseDetails?.apply {
            Timber.tag("ReleaseStat").i("Details: $this")
            if (activity == null) {
                return true
            }
            activity.shortToast(
                activity.resources.getString(
                    R.string.toast_update_available,
                    releaseDetails.shortVersion
                )
            )
            MaterialAlertDialogBuilder(activity).apply {
                setTitle("Update Available")
                setMessage("${releaseDetails.shortVersion} is available, download and install now?")
                setNegativeButton("Ignore this version") { dialog, which ->

                }
                setPositiveButton("Download and install") { dialog, which ->
                    val result = OneTimeWorkRequestBuilder<UpgradeWorker>()
                        .setInputData(
                            workDataOf(
                                UpgradeWorker.INPUT_DATA_DOWNLOAD_URL to downloadUrl.toString(),
                                UpgradeWorker.INPUT_DATA_VERSION_NAME to shortVersion
                            )
                        )
                        .build()
                }
            }.show()
        }
        return true
    }

    override fun onNoReleaseAvailable(activity: Activity?) {
        Timber.tag("ReleaseStat").d("No Update Available")
        activity?.apply {
            shortToast(resources.getString(R.string.toast_no_update_available))
        }
    }
}