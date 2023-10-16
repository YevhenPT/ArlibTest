package com.sts.investpuzzle.ui.login

import android.annotation.SuppressLint
import com.sts.investpuzzle.base.BaseViewModel
import com.sts.investpuzzle.roomdb.RegisterDatabase
import com.sts.investpuzzle.roomdb.RegisterEntity
import com.sts.investpuzzle.roomdb.Repository
import com.sts.investpuzzle.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.sts.investpuzzle.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@HiltViewModel
class LoginViewModel @Inject constructor(
    val schedulerProvider: SchedulerProvider,
    val compositeDisposable: CompositeDisposable,
    loginInteractor: LoginInteractor,
    @ApplicationContext context: Context

):BaseViewModel<LoginInteractor>(schedulerProvider, compositeDisposable,loginInteractor) {
    private val localDB = RegisterDatabase.getInstance(context)
    private val _users = MutableLiveData<Event<List<RegisterEntity>>>()
    val users : LiveData<Event<List<RegisterEntity>>> get() = _users
    var isUser:Boolean = false

     fun insertUser(userName:String, email:String){

        val dbDao = localDB?.localDao()
        dbDao?.insert(RegisterEntity(0,userName,email))

     }

     fun getUserName(userName:String){

             val userNames = localDB?.localDao()?.getUserName(userName )
             if (userNames!= null){
                 if (userNames.userName == userName){

                     isUser = true

                 }
             }else{
                 isUser = false
             }
    }

    fun getAllUsers(){

        val users =  localDB?.localDao()?.getAllUsers()
        if (users !=null){
            _users.postValue(Event(users))
        }
    }
}