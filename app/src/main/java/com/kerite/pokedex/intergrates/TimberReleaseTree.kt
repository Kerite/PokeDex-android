package com.kerite.pokedex.intergrates

import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class TimberReleaseTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        CoroutineScope(Dispatchers.Default).launch {
            super.log(priority, tag, message, t)
            t?.let { FirebaseCrashlytics.getInstance().recordException(it) }
        }
    }
}