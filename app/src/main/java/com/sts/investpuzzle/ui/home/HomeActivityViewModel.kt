package com.sts.investpuzzle.ui.home

import com.sts.investpuzzle.base.BaseViewModel
import com.sts.investpuzzle.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    val schedulerProvider: SchedulerProvider,
    val compositeDisposable: CompositeDisposable,
    homeActivityInteractor: HomeActivityInteractor
):BaseViewModel<HomeActivityInteractor>(schedulerProvider,compositeDisposable,homeActivityInteractor) {
}