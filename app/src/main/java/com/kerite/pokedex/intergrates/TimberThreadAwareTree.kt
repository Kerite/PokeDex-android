package com.kerite.pokedex.intergrates

import timber.log.Timber

class TimberThreadAwareTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val newTag = tag?.let {
            "<${Thread.currentThread().name}>$tag"
        }
        super.log(priority, newTag, message, t)
    }
}