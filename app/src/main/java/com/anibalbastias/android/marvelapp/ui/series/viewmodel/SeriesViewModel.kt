package com.anibalbastias.android.marvelapp.ui.series.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page.PageData
import com.anibalbastias.android.marvelapp.base.api.domain.series.usecase.GetSeriesUseCase
import com.anibalbastias.android.marvelapp.base.view.Resource
import com.anibalbastias.android.marvelapp.base.view.ResourceState
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class SeriesViewModel @Inject constructor(private val getSeriesUseCase: GetSeriesUseCase) : ViewModel() {

    //region LiveData vars
    private val getPageLiveData: MutableLiveData<Resource<PageData>> =
        MutableLiveData()
    private val pageDataLiveData: MutableLiveData<PageData> = MutableLiveData()
    //endregion

    var conversationListDataView: PageData?
        get() = pageDataLiveData.value
        set(value) {
            pageDataLiveData.value = value
        }

    override fun onCleared() {
        super.onCleared()
        getSeriesUseCase.dispose()
    }

    fun setPageDefaultState() {
        getPageLiveData.value = Resource(ResourceState.DEFAULT, null, null)
    }

    fun getPageLiveData(): MutableLiveData<Resource<PageData>> = getPageLiveData

    fun getSeriesData() {
        getPageLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getSeriesUseCase.execute(
            GetSeriesSubscriber(),
            Pair("v1", "public")
        )
    }

    inner class GetSeriesSubscriber : DisposableSubscriber<PageData>() {
        override fun onComplete() {}

        override fun onNext(t: PageData?) {
            t.let {
                getPageLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
            }
        }

        override fun onError(t: Throwable?) {
            t?.printStackTrace()
            getPageLiveData.postValue(Resource(ResourceState.ERROR, null, null))
        }
    }
}