package com.sts.investpuzzle.core.data.session

import android.annotation.SuppressLint
import android.content.Context
import com.androidnetworking.AndroidNetworking

import com.sts.investpuzzle.BuildConfig
import com.sts.investpuzzle.constants.AUTHORIZATION_PARAMETER
import com.sts.investpuzzle.constants.NETWORK_TIMEOUT
import com.sts.investpuzzle.core.data.network.ApiEndPoint
import com.sts.investpuzzle.core.data.network.WaffleParserFactory

import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.collections.HashMap

class AppSessionHelper @Inject constructor(@ApplicationContext val context : Context, val preferencesHelper: PreferencesHelper) : SessionHelper {

    /*private val connectedRef = Firebase.database.getReference(".info/connected")
    private val listenerRef = Firebase.database.getReference("/connection")*/

    override val apiEndPoint: ApiEndPoint = ApiEndPoint(preferencesHelper)
    override var authToken: MutableMap<String, String> = HashMap()

    override fun clearSession() {
        preferencesHelper.deleteSessionPreference()
        authToken.clear()
    }

    override var tokenExpirationDate: Calendar? = null

    override val internetConnectionObservable: Subject<Boolean> = PublishSubject.create()
    override var internetConnectionAvailable: Boolean = true

    override fun setAuthToken(authToken: String) {
        this.authToken[AUTHORIZATION_PARAMETER] = authToken
    }

    init {
        /*connectedRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    internetConnectionAvailable = true
                    internetConnectionObservable.onNext(true)
                    Log.d(TAG, "firebase connected")
                } else{
                    internetConnectionAvailable = false
                    internetConnectionObservable.onNext(false)
                    Log.d(TAG, "firebase not connected")
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        listenerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })*/

        initNetwork()
    }

    private fun initNetwork(){
        AndroidNetworking.initialize(context, getOkHttpClient(NETWORK_TIMEOUT))
        AndroidNetworking.setParserFactory(WaffleParserFactory().gsonParserFactory)
    }

    override fun getOkHttpClient(networkTimeout: Long): OkHttpClient? {
        return try {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(TokenRefreshInterceptor(this))
            builder.connectTimeout(networkTimeout, TimeUnit.SECONDS)
                .readTimeout(networkTimeout, TimeUnit.SECONDS)
                .writeTimeout(networkTimeout, TimeUnit.SECONDS)

            // disable here for image upload and release version
            /*if (BuildConfig.DEBUG) {
                val logging = okhttp3.logging.HttpLoggingInterceptor()
                logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(logging)
            }*/

            //If debug don't mind about the ssl certificate validity
            if (BuildConfig.DEBUG) {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                    @SuppressLint("CustomX509TrustManager")
                    object : X509TrustManager {
                        @SuppressLint("TrustAllX509TrustManager")
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _: String?, _: SSLSession? -> true }
            }

            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}