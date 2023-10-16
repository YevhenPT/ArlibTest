package com.sts.investpuzzle.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

import com.sts.investpuzzle.utils.Event

inline fun <T : Any?, L : LiveData<Event<T>>> AppCompatActivity.observeEvent(liveData : L, crossinline body : (T) -> Unit) {
    liveData.observe(this) { data ->
        data?.let { event ->
            whenNotNull(event.getContentIfNotHandled()) { body(it) }
        }
    }
}

inline fun <T : Any, L : LiveData<Event<T>>> Fragment.observeEvent(liveData: L, crossinline body: (T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner) { data ->
        data?.let { event ->
            whenNotNull(event.getContentIfNotHandled()) { body(it) }
        }
    }
}

inline fun <T: Any, R> whenNotNull(input : T?, callback: (T) -> R) : R? {
    return input?.let(callback)
}

inline fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, crossinline body: (T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner) { data -> whenNotNull(data) { body(it) } }
}

inline fun <T : Any, L : LiveData<T>> AppCompatActivity.observe(liveData: L, crossinline body: (T) -> Unit) {
    liveData.observe(this) { data -> whenNotNull(data) { body(it) } }
}
