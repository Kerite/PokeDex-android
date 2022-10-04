package com.kerite.pokedex.intergrates

import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class TimberThreadAwareTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val newTag = tag?.let {
            "<${Thread.currentThread().name}>$tag"
        }
        CoroutineScope(Dispatchers.Default).apply {
            super.log(priority, newTag, message, t)
            t?.let { FirebaseCrashlytics.getInstance().recordException(it) }
        }
    }
}