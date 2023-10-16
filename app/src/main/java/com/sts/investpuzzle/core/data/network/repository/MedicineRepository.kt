package com.sts.investpuzzle.core.data.network.repository

import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.sts.investpuzzle.core.data.network.model.medicine.MedicineResponse
import com.sts.investpuzzle.core.data.session.SessionHelper
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject


interface MedicineRepository{
    fun getMedicine(): Single<MedicineResponse>
}

class MedicineRepositoryImp @Inject
internal constructor(private val sessionHelper: SessionHelper):MedicineRepository{
    override fun getMedicine():Single<MedicineResponse> {

        return Rx2AndroidNetworking.get(sessionHelper.apiEndPoint.getMedicine)
            .build()
            .getObjectSingle(MedicineResponse::class.java)

    }

}