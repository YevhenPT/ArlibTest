package com.sts.investpuzzle.ui.medicine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.sts.investpuzzle.base.BaseViewModel
import com.sts.investpuzzle.core.data.network.model.medicine.Drug
import com.sts.investpuzzle.ui.login.UserModel
import com.sts.investpuzzle.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import com.sts.investpuzzle.utils.Event
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    val schedulerProvider: SchedulerProvider,
    val compositeDisposable: CompositeDisposable,
    medicineInteractor: MedicineInteractor,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MedicineInteractor>(schedulerProvider, compositeDisposable, medicineInteractor){

    private val drugList= mutableListOf<Drug>()

    private val _drugs = MutableLiveData<Event<List<Drug>>>()
    val drugs : LiveData<Event<List<Drug>>> get() = _drugs

    lateinit var sdf :SimpleDateFormat
    var user = savedStateHandle.get<UserModel>(USER)

    companion object {
        const val USER = "user"
    }

    fun getMedicine(){
       callInteractor(interactor.doMedicine()){

           val associatedDrug = it.problems.get(0).Diabetes.get(0).medications.get(0).medicationsClasses.get(0).className.map{ it.associatedDrug1.get(0)}
           val associatedDrug1 = it.problems.get(0).Diabetes.get(0).medications.get(0).medicationsClasses.get(0).className.map { it.associatedDrug2.get(0) }

           val associatedDrug3 = associatedDrug1.toMutableList() + associatedDrug.toMutableList()
           drugList.clear()
           drugList.addAll(associatedDrug3.toList())

           _drugs.value = Event(drugList)


           Log.d("Response===>", drugList.toString())
       }
    }

    fun getTime():String{
        sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val currentDate = sdf.format(Date())

        return currentDate
    }

}


