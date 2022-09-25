package com.kerite.pokedex.intergrates

import android.util.Log
import com.microsoft.appcenter.crashes.Crashes
import timber.log.Timber

class TimberReleaseTree : Timber.DebugTree() {
    override fun log(priority: Int, t: Throwable?) {
        super.log(priority, t)

        t?.let { throwable ->
            if (priority == Log.ERROR) {
//                val stack = Log.getStackTraceString(throwable)
                Crashes.trackError(throwable)
            }
        }
    }
}