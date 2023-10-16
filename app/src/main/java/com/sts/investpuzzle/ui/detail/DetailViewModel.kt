package com.sts.investpuzzle.ui.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.sts.investpuzzle.base.BaseViewModel
import com.sts.investpuzzle.core.data.network.model.medicine.Drug
import com.sts.investpuzzle.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val schedulerProvider: SchedulerProvider,
    val compositeDisposable: CompositeDisposable,
    detailInteractor: DetailInteractor,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context
):BaseViewModel<DetailInteractor>(schedulerProvider, compositeDisposable, detailInteractor) {

    companion object {
        const val MEDICINE = "medicine"
    }
    var medicine = savedStateHandle.get<Drug>(MEDICINE)
}