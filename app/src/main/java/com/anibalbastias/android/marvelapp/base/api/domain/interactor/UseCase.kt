package com.anibalbastias.android.marvelapp.base.api.domain.interactor

import com.anibalbastias.android.marvelapp.base.api.domain.executor.APIPostExecutionThread
import com.anibalbastias.android.marvelapp.base.api.domain.executor.APIThreadExecutor
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class UseCase<T> protected constructor(private val threadExecutor: APIThreadExecutor,
                                                private val postExecutionThread: APIPostExecutionThread
) {

    protected var disposable = Disposables.empty()

    val isUnsubscribed: Boolean
        get() = this.disposable.isDisposed

    protected abstract fun buildUseCaseObservable(): Observable<T>?

    fun <S> execute(useCaseDisposable: S) where S : Observer<T>, S : Disposable {
        this.disposable = this.buildUseCaseObservable()
                ?.subscribeOn(Schedulers.from(threadExecutor))
                ?.observeOn(postExecutionThread.scheduler)
                ?.subscribeWith(useCaseDisposable)
    }

    fun unsubscribe() {
        if (!this.disposable.isDisposed) {
            this.disposable.dispose()
        }
    }

}
