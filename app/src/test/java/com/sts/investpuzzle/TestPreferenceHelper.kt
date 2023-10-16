package com.sts.investpuzzle

import com.sts.investpuzzle.core.data.network.model.user_management.SignInRequest
import com.sts.investpuzzle.core.data.prefs.PreferencesHelper

class TestPreferenceHelper: PreferencesHelper {

    override var signupTryCount: Int = 3;
    override var lastSignupTryTime: Long = 0;

    override fun deleteSessionPreference() {
        TODO("Not yet implemented")
    }

    override fun saveSignInRequest(signInRequest: SignInRequest) {
        TODO("Not yet implemented")
    }

    override fun getSignInRequest(): SignInRequest? {
        TODO("Not yet implemented")
    }
}