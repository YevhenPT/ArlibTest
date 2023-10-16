package com.sts.investpuzzle.core.data.session

import android.util.Log
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class TokenRefreshInterceptor(private var  appSessionHelper: SessionHelper) : Interceptor{

    companion object {
        private const val AUTH_PATH = "/auth/login"
        private const val TIME_TO_REFRESH = 60
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request : Request = chain.request()

        if (request.url.encodedPath == AUTH_PATH)
            return chain.proceed(request)

        val timeToRefreshInSeconds: Long = getTimeToRefresh()

        //refresh token TIME_TO_REFRESH seconds before expiring
        if (timeToRefreshInSeconds + TIME_TO_REFRESH > 0) {
            try {
                //set new headers for current request
                request = request.newBuilder()
                    .headers(appSessionHelper.authToken.toHeaders())
                    .build()
            } catch (ex: Exception) {
                Log.d("RefreshInterceptor", "error refreshing token: $ex")
            }
        }

        return chain.proceed(request)
    }

    private fun getTimeToRefresh(): Long {
        val now: Calendar = Calendar.getInstance()
        var expirationDate: Calendar? = appSessionHelper.tokenExpirationDate

        //force token renewal if no expiration date found
        if (expirationDate == null) {
            expirationDate = now
        }
        return (now.timeInMillis - expirationDate.timeInMillis) / 1000
    }
}