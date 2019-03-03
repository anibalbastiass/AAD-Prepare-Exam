package com.anibalbastias.android.marvelapp.ui.series

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.anibalbastias.android.marvelapp.R
import com.anibalbastias.android.marvelapp.appComponent
import com.anibalbastias.android.marvelapp.base.module.ViewModelFactory
import com.anibalbastias.android.marvelapp.base.module.getViewModel
import com.anibalbastias.android.marvelapp.base.view.BaseModuleFragment
import com.anibalbastias.android.marvelapp.databinding.FragmentSeriesBinding
import com.anibalbastias.android.marvelapp.ui.series.viewmodel.SeriesViewModel
import com.anibalbastias.android.marvelapp.util.applyFontForToolbarTitle
import com.anibalbastias.android.marvelapp.util.setNoArrowUpToolbar
import javax.inject.Inject

class SeriesFragment : BaseModuleFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_series

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var seriesViewModel: SeriesViewModel

    private var binding: FragmentSeriesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        seriesViewModel = getViewModel(viewModelFactory)
        setHasOptionsMenu(true)
    }

    operator fun invoke(): SeriesFragment {

        val args = Bundle()
        val fragment = SeriesFragment()

        fragment.run {
            //            stockPathFile = stockPath
        }

        fragment.arguments = args
        return fragment

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentSeriesBinding
        binding?.lifecycleOwner = this

        initToolbar()
    }

    private fun initToolbar() {
        binding?.seriesToolbar?.title = getString(R.string.series_toolbar)
        binding?.seriesToolbar?.applyFontForToolbarTitle(activity!!)
        binding?.seriesToolbar?.setNoArrowUpToolbar(activity!!)
        binding?.seriesToolbar?.setNavigationOnClickListener {
            activity?.finish()
        }
    }
}