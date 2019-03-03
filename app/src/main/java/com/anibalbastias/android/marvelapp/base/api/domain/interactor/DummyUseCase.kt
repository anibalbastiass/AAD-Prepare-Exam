package com.anibalbastias.android.marvelapp.base.api.domain.interactor

import com.anibalbastias.android.marvelapp.base.api.domain.executor.APIPostExecutionThread
import com.anibalbastias.android.marvelapp.base.api.domain.executor.APIThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by joseignacio on 03-10-17.
 */

class DummyUseCase<T> @Inject constructor(
    threadExecutor: APIThreadExecutor,
    postExecutionThread: APIPostExecutionThread
) : UseCase<T>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(): Observable<T>? {
        return null
    }
}
