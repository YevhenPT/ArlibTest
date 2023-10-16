package com.sts.investpuzzle.ui.login

import com.sts.investpuzzle.base.BaseInteractor
import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import javax.inject.Inject

class LoginInteractor @Inject internal constructor(

    preferencesHelper: PreferencesHelper,
    sessionHelper: SessionHelper
):BaseInteractor(preferencesHelper,sessionHelper){
}