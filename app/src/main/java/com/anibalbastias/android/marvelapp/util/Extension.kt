package com.anibalbastias.android.marvelapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.anibalbastias.android.marvelapp.GlideApp
import com.anibalbastias.android.marvelapp.MarvelApplication.Companion.appContext
import com.anibalbastias.android.marvelapp.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.initObserver(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    try {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, Observer<T> { t -> observer.invoke(t!!) })
    } catch (e: KotlinNullPointerException) {
        e.printStackTrace()
    }

}

fun <T> LiveData<T>.initObserverForever(observer: Observer<T>) {
    removeObserver(observer)
    observeForever { t -> observer.onChanged(t) }
}

@SuppressLint("ShowToast")
fun Activity.getToastTypeFaced(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    val typeface = ResourcesCompat.getFont(applicationContext!!, R.font.opensans_regular)
    val toastLayout = toast.view as? LinearLayout
    val toastTV = (toastLayout?.getChildAt(0) as? TextView)
    toastTV?.typeface = typeface
    return toast
}

fun Activity.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
    text?.let {
        getToastTypeFaced(text, duration).show()
    }

@SuppressLint("SimpleDateFormat")
fun getTs(): String {
    val tsLong = Date().time / 1000
    return tsLong.toString()
}

fun getMarvelAPIHash() =
    (getTs() +
            appContext.getString(R.string.marvel_private_api_key) +
            appContext.getString(R.string.marvel_public_api_key)
            ).md5()

fun String.Companion.empty() = ""

var etagPage: String? = String.empty()

fun ImageView.loadImageWithoutPlaceholder(url: String) =
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(true)
        .into(this)

fun isTablet(context: Context): Boolean = try {
    context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}

fun isPhone(context: Context): Boolean = try {
    !context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}


fun isPortrait(context: Context): Boolean = try {
    context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
} catch (ex: Exception) {
    false
}

fun launchUrlInCustomTabBase(activity: Activity, url: String) {

    try {
        val chrome = arrayOf("com.android.chrome",
            "com.chrome.beta",
            "com.chrome.dev",
            "com.google.android.apps.chrome")

        var isChromeInstall = false
        chrome.forEach {
            try {
                activity.packageManager.getPackageInfo(it, 0)
                isChromeInstall = true
                return@forEach
            } catch (nameNotFoundException: PackageManager.NameNotFoundException) {
            }
        }
        if (!isChromeInstall) {
//            activity.launchUrlWebView(url)
            return
        }

        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setToolbarColor(ContextCompat.getColor(activity, R.color.primaryColor))
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(activity, R.color.primaryColor))

        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))

    } catch (exception: Exception) {
        activity.toast(activity.getString(R.string.generic_error_message))
    }
}