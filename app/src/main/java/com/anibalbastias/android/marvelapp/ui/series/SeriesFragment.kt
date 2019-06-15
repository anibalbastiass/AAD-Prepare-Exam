package com.anibalbastias.android.marvelapp.ui.series

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.anibalbastias.android.marvelapp.R
import com.anibalbastias.android.marvelapp.appComponent
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page.PageData
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.SeriesItemData
import com.anibalbastias.android.marvelapp.base.module.ViewModelFactory
import com.anibalbastias.android.marvelapp.base.module.getViewModel
import com.anibalbastias.android.marvelapp.base.view.BaseModuleFragment
import com.anibalbastias.android.marvelapp.base.view.ResourceState
import com.anibalbastias.android.marvelapp.databinding.FragmentSeriesBinding
import com.anibalbastias.android.marvelapp.ui.series.adapter.SeriesListAdapter
import com.anibalbastias.android.marvelapp.ui.series.viewmodel.SeriesViewModel
import com.anibalbastias.android.marvelapp.util.*
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

        initViewModel()
        initToolbar()

        seriesViewModel.conversationListDataView?.let {
            setPageData(it)
        } ?: seriesViewModel.getSeriesData()
    }

    private fun initViewModel() {
        seriesViewModel.getPageLiveData().initObserver(this@SeriesFragment) {
            if (it != null) {
                this.handlePageData(it.status, it.data, it.message)
            }
        }
    }

    private fun handlePageData(status: ResourceState, data: PageData?, message: String?) {
        binding?.seriesLoader?.gone()
        when (status) {
            ResourceState.DEFAULT -> {
            }
            ResourceState.LOADING -> showLoadingView()
            ResourceState.SUCCESS -> setPageData(data!!)
            ResourceState.ERROR -> showErrorView(message)
            else -> {
            }
        }
    }

    private fun showErrorView(message: String?) {

    }

    private fun showLoadingView() {
        binding?.seriesLoader?.visible()
    }

    private fun setPageData(data: PageData) {
        // Set Adapter
        setAdapter(data?.data?.results)
    }

    private fun setAdapter(results: List<SeriesItemData>?) {
        binding?.seriesListRecyclerView?.layoutManager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL, false
        )

        val adapter = SeriesListAdapter()
//        adapter?.itemCallback = this
        adapter?.items = results as MutableList<SeriesItemData>?
        binding?.seriesListRecyclerView?.adapter = adapter
    }

    private fun initToolbar() {
        binding?.seriesToolbar?.run {
            title = getString(R.string.series_toolbar)
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
            setNavigationOnClickListener {
                activity?.finish()
            }
        }
    }
}