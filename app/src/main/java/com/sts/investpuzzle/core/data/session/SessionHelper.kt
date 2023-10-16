package com.sts.investpuzzle.core.data.session

import com.sts.investpuzzle.core.data.network.ApiEndPoint
import io.reactivex.subjects.Subject
import okhttp3.OkHttpClient
import java.util.*
import javax.inject.Singleton

@Singleton
interface SessionHelper {
    fun clearSession()
    var tokenExpirationDate: Calendar?
    val internetConnectionObservable : Subject<Boolean>
    var internetConnectionAvailable: Boolean
    val apiEndPoint: ApiEndPoint
    var authToken : MutableMap<String, String>
    fun setAuthToken(authToken: String)
    fun getOkHttpClient(networkTimeout: Long): OkHttpClient?
}