package com.example.courierdelivery.viewModels.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.MainActivityModel
import entities.Event

sealed class MainActivityVMStates {
    object Default : MainActivityVMStates()
}

class MainActivityViewModel(
    //val model: MainActivityModel,
) : ViewModel() {

    private var _navigationEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigationEvent: LiveData<Event<Unit>> = _navigationEvent

    fun onNavigationClick() {
        _navigationEvent.value = Event(Unit)
    }

}