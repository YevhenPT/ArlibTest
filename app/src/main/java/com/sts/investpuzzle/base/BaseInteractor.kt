package com.sts.investpuzzle.base

import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import io.reactivex.Single

open class BaseInteractor constructor(override val preferencesHelper : PreferencesHelper,
                              override val sessionHelper: SessionHelper) : Interactor {
    override fun dummyCall(): Single<Int> = Single.just(1)
}