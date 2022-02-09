package com.devexperto.architectcoders.ui.common

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.core.content.IntentCompat
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

inline fun <T : Any> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}

inline fun <reified T> Intent.getParcelableExtraCompat(name: String?): T? {
    return IntentCompat.getParcelableExtra(this, name, T::class.java)
}

@Suppress("DEPRECATION")
suspend fun Geocoder.getFromLocationCompat(
    @FloatRange(from = -90.0, to = 90.0) latitude: Double,
    @FloatRange(from = -180.0, to = 180.0) longitude: Double,
    @IntRange maxResults: Int
): List<Address> = suspendCancellableCoroutine { continuation ->
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, maxResults) {
            continuation.resume(it)
        }
    } else {
        continuation.resume(getFromLocation(latitude, longitude, maxResults) ?: emptyList())
    }
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }