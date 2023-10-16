package com.sts.investpuzzle.ui.medicine

import com.sts.investpuzzle.base.BaseInteractor
import com.sts.investpuzzle.core.data.network.model.medicine.MedicineResponse
import com.sts.investpuzzle.core.data.network.repository.MedicineRepository
import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import io.reactivex.Single
import javax.inject.Inject

class MedicineInteractor @Inject internal  constructor(
    preferencesHelper: PreferencesHelper,
    sessionHelper: SessionHelper,
   private val medicineRepository: MedicineRepository
): BaseInteractor(preferencesHelper, sessionHelper){

    fun doMedicine(): Single<MedicineResponse>  {
        return medicineRepository.getMedicine()
    }

}