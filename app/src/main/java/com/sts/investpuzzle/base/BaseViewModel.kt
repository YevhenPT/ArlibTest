package com.sts.investpuzzle.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.error.ANError
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.sts.investpuzzle.constants.*
import com.sts.investpuzzle.utils.Event
import com.sts.investpuzzle.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

open class BaseViewModel<I : BaseInteractor> constructor(
    val mSchedulerProvider: SchedulerProvider,
    val mCompositeDisposable: CompositeDisposable,
    val interactor: I
) : ViewModel() {



    val progressVisible = MutableLiveData<Event<Boolean>>()
    val toastMessage = MutableLiveData<Event<String>>()
    val errorMessage = MutableLiveData<Event<Pair<String, String>>>()
    val asyncLoadDone = MutableLiveData<Boolean>()
    val showInternetConnectionError = MutableLiveData<Event<Boolean>>()
    var signInStatus = false

    fun showLoading() {progressVisible.postValue(Event(true))}
    fun hideLoading() {progressVisible.postValue(Event(false))}
    fun showToast(msg : String) { toastMessage.postValue(Event(msg)) }
    fun showError(errorTitle : String, errorMsg : String) {
        errorMessage.postValue(Event(Pair(errorTitle, errorMsg)))
    }

    init {
        mCompositeDisposable.add(
            interactor.sessionHelper.internetConnectionObservable
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    showInternetConnectionError.value = Event(it)
                }, errorConsumer(null)))

        asyncLoadDone.value = false
    }

    fun setupAsync() { asyncLoadDone.value = true }

    protected fun <T> callInteractor(interactorSingleMethod : Single<T>,  isShowLoading : Boolean = true, errorHandlerCaller: ErrorHandlerCaller = ErrorHandlerCaller.NONE,
                                     errorCallback: ((Throwable) -> Unit)? = null, consumer: (T) -> Unit) {
        if (!interactor.sessionHelper.internetConnectionAvailable){
            showError(NO_INTERNET, NO_INTERNET_AVAILABLE)
            return
        }
        if (isShowLoading)
            showLoading()
        mCompositeDisposable.add(
            interactorSingleMethod
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doFinally { hideLoading() }
                .subscribe({
                    consumer(it)
                }, errorConsumer(null, errorHandlerCaller, errorCallback))
        )
    }

    private fun errorConsumer(@StringRes errorTitle : Int?, errorHandlerCaller : ErrorHandlerCaller = ErrorHandlerCaller.NONE, errorCallback : ((Throwable) -> Unit)? = null) : Consumer<Throwable> {
        return Consumer { throwable : Throwable ->
            if (throwable is ANError) {
                handleApiError(throwable, errorTitle, errorHandlerCaller)
            }else {
                showError("Oops, something went wrong. Please contact us", WAFFLE_ERROR_NOT_ANERROR)
            }
            errorCallback?.let { it(throwable) }
        }
    }

    private fun handleApiError(error : ANError, errorTitle : Int?, errorHandlerCaller : ErrorHandlerCaller){
        val apiError : APIError = Gson().fromJson(error.errorBody, APIError::class.java)
        showError(COMMON_ERROR_TITLE, apiError.message)
    }

    fun signout(){
        interactor.sessionHelper.clearSession()
    }

    override fun onCleared() {
        hideLoading()
        mCompositeDisposable.dispose()
    }

    data class APIError(
        @SerializedName("message") val message: String,
    )
}