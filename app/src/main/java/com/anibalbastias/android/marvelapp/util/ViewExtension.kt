package com.anibalbastias.android.marvelapp.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.anibalbastias.android.marvelapp.R
import com.crashlytics.android.Crashlytics

fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.disableAndPostDelayEnabled() {
    this.isEnabled = false
    Handler().postDelayed({
        this.isEnabled = true
    }, 1500)
}

fun View.showKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 1)
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.hideKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
        imm.hideSoftInputFromInputMethod(this.windowToken, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun dpToPx(dp: Float): Int =
    (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics)).toInt()

fun dpToPx(dp: Int) = dpToPx(dp.toFloat())

fun spToPx(sp: Int) = spToPx(sp.toFloat())

fun spToPx(sp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics).toInt()

fun pxToDP(pixels: Int) =
    Math.floor((pixels / (Resources.getSystem().displayMetrics.densityDpi / 160f)).toDouble()).toInt()

fun Toolbar.applyFontForToolbarTitle(context: Activity, color: Int = R.color.primaryColor) {
    for (i in 0 until this.childCount) {
        val view = this.getChildAt(i)
        if (view is TextView) {
            try {
                val titleFont = ResourcesCompat.getFont(context, R.font.opensans_regular)
                val subtitleFont = ResourcesCompat.getFont(context, R.font.opensans_regular)
                if (view.text == this.title) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
                    view.setTextColor(resources.getColor(color))
                    view.typeface = titleFont
                } else if (view.text == this.subtitle) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f)
                    view.setTextColor(resources.getColor(color))
                    view.typeface = subtitleFont
                    break
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }
        }
    }
    (context as AppCompatActivity).setSupportActionBar(this)
}

fun Toolbar.setArrowUpToolbar(context: Activity) {
    (context as AppCompatActivity).setSupportActionBar(this)
    context.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}

fun Toolbar.setNoArrowUpToolbar(context: Activity) {
    (context as AppCompatActivity).setSupportActionBar(this)
    context.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
}