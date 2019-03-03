package com.anibalbastias.android.marvelapp.base.api.domain.executor

import io.reactivex.Scheduler

interface APIPostExecutionThread {
    val scheduler: Scheduler
}
