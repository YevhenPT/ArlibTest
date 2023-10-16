package com.sts.investpuzzle.base

import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import io.reactivex.Single

interface Interactor {

    val preferencesHelper : PreferencesHelper
    val sessionHelper : SessionHelper

    fun dummyCall() : Single<Int>
}