package com.anibalbastias.android.marvelapp

import android.content.Context
import android.support.multidex.MultiDexApplication
import android.support.v4.app.Fragment
import com.anibalbastias.android.marvelapp.base.view.BaseModuleFragment
import com.anibalbastias.android.marvelapp.component.ApplicationComponent
import com.anibalbastias.android.marvelapp.component.DaggerApplicationComponent
import com.anibalbastias.android.marvelapp.module.ApplicationModule

class MarvelApplication : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
        var applicationComponent: ApplicationComponent? = null
    }

    init {
        //Initialize Tenant Always first

    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
        appContext = this
    }
}

private fun buildDagger(context: Context): ApplicationComponent {
    if (MarvelApplication.applicationComponent == null) {
        MarvelApplication.applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(context as MarvelApplication))
            .build()
    }
    return MarvelApplication.applicationComponent!!
}

fun Context.appComponent(): ApplicationComponent {
    return buildDagger(this.applicationContext)
}

fun Fragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}

fun BaseModuleFragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}