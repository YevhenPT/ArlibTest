package com.sts.investpuzzle.core.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(@ApplicationContext val context: Context) : PreferencesHelper{

    private val PREF_NAME = "Arlib"
    private val SIGNUP_TRY_COUNT = "SIGNUP_TRY_COUNT"
    private val LAST_SIGNUP_TRY_TIME = "LAST_SIGNUP_TRY_TIME"
    private val SIGIN_REQUEST = "SIGNIN_REQUEST"

    private val mPrefs : SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    override var signupTryCount: Int
        get() = mPrefs.getInt(SIGNUP_TRY_COUNT, 0)
        set(value) = mPrefs.edit().putInt(SIGNUP_TRY_COUNT, value).apply()
    override var lastSignupTryTime: Long
        get() = mPrefs.getLong(LAST_SIGNUP_TRY_TIME, 0)
        set(value) = mPrefs.edit().putLong(LAST_SIGNUP_TRY_TIME, value).apply()

    override fun deleteSessionPreference() {
        mPrefs.edit().remove(SIGIN_REQUEST).apply()
    }

}