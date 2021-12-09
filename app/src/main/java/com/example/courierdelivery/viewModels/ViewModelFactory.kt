package com.example.courierdelivery.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courierdelivery.viewModels.shared.SplashScreenActAuthFragSharedViewModel
import di.AppComponent

object ViewModelFactory : ViewModelProvider.Factory {
    lateinit var appComponent: AppComponent
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            SplashScreenActAuthFragSharedViewModel::class.java ->
                SplashScreenActAuthFragSharedViewModel(appComponent.provideSplashScreenModel())
            else -> throw IllegalArgumentException("Unknown viewModel class")
        }
        return viewModel as T
    }

}
