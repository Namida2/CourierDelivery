package com.example.courierdelivery.viewModels.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.SplashScreenModelInterface
import extensions.logD

sealed class SplashScreenVMStates {
    object Default : SplashScreenVMStates()
    object NotAuthorized : SplashScreenVMStates()
    object Authorized : SplashScreenVMStates()
}

class SplashScreenActAuthFragSharedViewModel(
    private val model: SplashScreenModelInterface,
) : ViewModel() {

    private var _state: MutableLiveData<SplashScreenVMStates> =
        MutableLiveData(SplashScreenVMStates.Default)
    val state: LiveData<SplashScreenVMStates> = _state

    fun checkCurrentUser() {
        val result: String? = model.getCurrentUser()
        if (result == null) {
            _state.value = SplashScreenVMStates.NotAuthorized
            return
        }
        logD("Reading the data by number here")
        _state.value = SplashScreenVMStates.Authorized
    }

}