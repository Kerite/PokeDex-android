package com.kerite.pokedex.intergrates

import android.app.Activity
import com.kerite.fission.android.extensions.shortToast
import com.kerite.pokedex.R
import com.microsoft.appcenter.distribute.DistributeListener
import com.microsoft.appcenter.distribute.ReleaseDetails
import timber.log.Timber

class PokeDexDistributeListener : DistributeListener {
    override fun onReleaseAvailable(activity: Activity?, releaseDetails: ReleaseDetails?): Boolean {
        Timber.tag("ReleaseStat").i("Update Available")
        releaseDetails?.apply {
            Timber.tag("ReleaseStat").i("Details: $this")
            activity?.apply {
                shortToast(resources.getString(R.string.toast_update_available, releaseDetails.shortVersion))
            }
        }
        return false
    }

    override fun onNoReleaseAvailable(activity: Activity?) {
        Timber.tag("ReleaseStat").d("No Update Available")
        activity?.apply {
            shortToast(resources.getString(R.string.toast_no_update_available))
        }
    }
}