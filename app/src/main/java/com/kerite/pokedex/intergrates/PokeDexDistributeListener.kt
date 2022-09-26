package com.kerite.pokedex.intergrates

import android.app.Activity
import com.kerite.pokedex.util.extension.shortToast
import com.microsoft.appcenter.distribute.DistributeListener
import com.microsoft.appcenter.distribute.ReleaseDetails
import timber.log.Timber

class PokeDexDistributeListener : DistributeListener {
    override fun onReleaseAvailable(activity: Activity?, releaseDetails: ReleaseDetails?): Boolean {
        Timber.tag("ReleaseStat").i("Update Available")
        releaseDetails?.apply {
            Timber.tag("ReleaseStat").i("Details: $this")
        }
        activity?.shortToast("更新可用")
        return false
    }

    override fun onNoReleaseAvailable(activity: Activity?) {
        Timber.tag("ReleaseStat").d("No Update Available")
    }
}