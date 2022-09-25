package com.kerite.pokedex.intergrates

import android.util.Log
import com.microsoft.appcenter.crashes.Crashes
import timber.log.Timber

class TimberReleaseTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
        t?.let { throwable ->
            if (priority == Log.ERROR) {
                Crashes.trackError(throwable)
            }
        }
    }
}