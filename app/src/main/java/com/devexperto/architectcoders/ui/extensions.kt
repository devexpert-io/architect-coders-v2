package com.devexperto.architectcoders.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

inline fun <reified T> Intent.getParcelableExtraCompat(name: String?): T? {
    return IntentCompat.getParcelableExtra(this, name, T::class.java)
}
