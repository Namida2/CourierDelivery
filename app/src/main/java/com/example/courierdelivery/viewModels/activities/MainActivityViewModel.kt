package com.example.courierdelivery.viewModels.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.MainActivityModel

sealed class MainActivityVMStates {
    object Default : MainActivityVMStates()
}

class MainActivityViewModel(
    val model: MainActivityModel,
) : ViewModel() {

    private var _state: MutableLiveData<MainActivityVMStates> =
        MutableLiveData(MainActivityVMStates.Default)
    val state: LiveData<MainActivityVMStates> = _state


}