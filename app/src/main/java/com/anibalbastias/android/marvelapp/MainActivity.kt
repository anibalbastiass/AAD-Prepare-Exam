package com.anibalbastias.android.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anibalbastias.android.marvelapp.ui.series.SeriesFragment

class MainActivity : AppCompatActivity() {

    private val seriesFragment: SeriesFragment by lazy {
        SeriesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent().inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, seriesFragment.invoke())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
