package com.sts.investpuzzle

import com.sts.investpuzzle.base.BaseInteractor

import io.mockk.every
import io.mockk.mockk
import io.reactivex.subjects.PublishSubject

class InteractorSetup {

    companion object {
        fun setInteractorDefaults(interactor: BaseInteractor) {
            every { interactor.sessionHelper.internetConnectionObservable } returns PublishSubject.create()
            every { interactor.sessionHelper.internetConnectionAvailable } returns true

        }
    }
}