package com.sts.investpuzzle.ui.detail

import com.sts.investpuzzle.base.BaseInteractor
import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import javax.inject.Inject

class DetailInteractor @Inject internal constructor(

    preferencesHelper: PreferencesHelper,
    sessionHelper: SessionHelper
):BaseInteractor(preferencesHelper,sessionHelper)