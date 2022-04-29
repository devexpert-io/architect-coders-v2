package com.devexperto.architectcoders.ui

import androidx.annotation.CheckResult
import androidx.test.espresso.IdlingResource
import okhttp3.Dispatcher
import okhttp3.OkHttpClient


class OkHttp3IdlingResource private constructor(
    private val name: String,
    private val dispatcher: Dispatcher
) :
    IdlingResource {

    @Volatile
    var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = name

    override fun isIdleNow(): Boolean = dispatcher.runningCallsCount() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    companion object {
        /**
         * Create a new [IdlingResource] from `client` as `name`. You must register
         * this instance using `Espresso.registerIdlingResources`.
         */
        @CheckResult
        fun create(name: String, client: OkHttpClient): OkHttp3IdlingResource {
            return OkHttp3IdlingResource(name, client.dispatcher)
        }
    }

    init {
        dispatcher.idleCallback = Runnable { callback?.onTransitionToIdle() }
    }
}