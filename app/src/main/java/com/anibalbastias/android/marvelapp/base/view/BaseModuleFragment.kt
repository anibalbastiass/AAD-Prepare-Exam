package com.anibalbastias.android.marvelapp.base.view

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anibalbastias.android.marvelapp.util.inflate

abstract class BaseModuleFragment : Fragment() {

    abstract fun tagName(): String
    abstract fun layoutId(): Int

    var mResources: Resources? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        mResources = resources

        try {
            return container?.inflate(layoutId())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return null
    }
}