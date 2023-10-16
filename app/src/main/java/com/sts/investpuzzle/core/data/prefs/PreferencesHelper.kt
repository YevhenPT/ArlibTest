package com.sts.investpuzzle.core.data.prefs

import javax.inject.Singleton

@Singleton
interface PreferencesHelper {
    var signupTryCount : Int
    var lastSignupTryTime : Long
    fun deleteSessionPreference()

}