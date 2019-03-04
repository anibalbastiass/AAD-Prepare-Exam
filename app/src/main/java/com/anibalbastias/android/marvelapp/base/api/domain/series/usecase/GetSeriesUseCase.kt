package com.anibalbastias.android.marvelapp.base.api.domain.series.usecase

import au.com.carsales.apibaselib.domain.interactor.FlowableUseCase
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page.PageData
import com.anibalbastias.android.marvelapp.base.api.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.marvelapp.base.api.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.marvelapp.base.api.domain.series.repository.ISeriesRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val detailRepository: ISeriesRepository,
    threadExecutor: APIThreadExecutor,
    postExecutionThread: APIPostExecutionThread
) : FlowableUseCase<PageData, Pair<String, String>>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Pair<String, String>?): Flowable<PageData> {
        return detailRepository.getSeriesData(params?.first!!, params.second)
    }
}