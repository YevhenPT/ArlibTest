package com.sts.investpuzzle.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.sts.investpuzzle.utils.Event

open class SharedViewModel : ViewModel() {

    private val _reloadPortfolio = MutableLiveData<Event<Boolean>>()
    val reloadPortfolio : LiveData<Event<Boolean>> get() = _reloadPortfolio

    private val _reloadQuestion = MutableLiveData<Event<Boolean>>()
    val reloadQuestion : LiveData<Event<Boolean>> get() = _reloadQuestion

    private val _deletedQuestionId = MutableLiveData<Event<String>>()
    val deletedQuestionId : LiveData<Event<String>> get() = _deletedQuestionId

    private val _showUniversityRanking = MutableLiveData<Event<Boolean>>()
    val showUniversityRanking : LiveData<Event<Boolean>> get() = _showUniversityRanking

    private val _showRankingPage = MutableLiveData<Event<Boolean>>()
    val showRankingPage : LiveData<Event<Boolean>> get() = _showRankingPage

    private val _isReloadFavoriteStocks = MutableLiveData<Event<Boolean>>()
    val isReloadFavoriteStocks : LiveData<Event<Boolean>> get() = _isReloadFavoriteStocks


    private val _reloadGroup = MutableLiveData<Event<Boolean>>()
    val reloadGroup : LiveData<Event<Boolean>> get() = _reloadGroup

    private val _reloadFollowings = MutableLiveData<Event<Boolean>>()
    val reloadFollowings : LiveData<Event<Boolean>> get() = _reloadFollowings

    fun reloadPortfolio(){
        _reloadPortfolio.value = Event(true);
    }
    fun reloadQuestion(){
        _reloadQuestion.value = Event(true)
    }

    fun questionDeleted(deletedQuestionId : String){
        _deletedQuestionId.value = Event(deletedQuestionId)
    }

    fun showUniversityRanking(){
        _showUniversityRanking.value = Event(true)
    }

    fun showRankingPage(){
        _showRankingPage.value = Event(true)
    }

    fun reloadFollowings() {
        _reloadFollowings.value = Event(true)
    }

    fun reloadGroup(){
        _reloadGroup.value = Event(true)
    }


    fun reloadFavoriteStocks(value : Boolean){
        _isReloadFavoriteStocks.value = Event(value)
    }
}