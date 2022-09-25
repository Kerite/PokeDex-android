package com.kerite.pokedex.intergrates

import android.app.Activity
import com.kerite.pokedex.util.extension.shortToast
import com.microsoft.appcenter.distribute.DistributeListener
import com.microsoft.appcenter.distribute.ReleaseDetails

class PokeDexDistributeListener : DistributeListener {
    override fun onReleaseAvailable(activity: Activity?, releaseDetails: ReleaseDetails?): Boolean {
        activity?.shortToast("更新可用")
        return false
    }

    override fun onNoReleaseAvailable(activity: Activity?) {
        activity?.shortToast("更新可用")
    }
}