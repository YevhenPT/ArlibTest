package com.sts.investpuzzle.core.data.network

import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiEndPoint @Inject constructor(private val preferencesHelper: PreferencesHelper) {

    val apiURL = "https://run.mocky.io/v3/029acb35-60fe-438b-9d42-6d32d1baa4a4"
    val getMedicine:String get() =  apiURL
}