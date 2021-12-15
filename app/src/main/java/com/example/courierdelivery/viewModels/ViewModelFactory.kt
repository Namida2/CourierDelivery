package com.example.courierdelivery.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courierdelivery.viewModels.activities.MainActivityViewModel
import com.example.courierdelivery.viewModels.fragments.AuthFragmentViewModel
import com.example.courierdelivery.viewModels.activities.SplashScreenActAuthFragSharedViewModel
import com.example.courierdelivery.viewModels.dialogs.RouteItemDialogMenuViewModel
import com.example.courierdelivery.viewModels.fragments.MapFragmentViewModel
import com.example.courierdelivery.viewModels.fragments.RouteMapsDetailViewModel
import com.example.courierdelivery.viewModels.fragments.RouteMapsFragmentViewModel
import di.AppComponent

object ViewModelFactory : ViewModelProvider.Factory {
    lateinit var appComponent: AppComponent
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            MapFragmentViewModel::class.java ->
                MapFragmentViewModel(appComponent.provideMapFragmentModel())
            RouteItemDialogMenuViewModel::class.java ->
                RouteItemDialogMenuViewModel(appComponent.provideRouteItemDialogMenuModel())
            RouteMapsDetailViewModel::class.java ->
                RouteMapsDetailViewModel(appComponent.provideRouteMapsDetailModel())
            MainActivityViewModel::class.java ->
                MainActivityViewModel()
            RouteMapsFragmentViewModel::class.java ->
                RouteMapsFragmentViewModel(appComponent.provideRouteMapsModel())
            AuthFragmentViewModel::class.java ->
                AuthFragmentViewModel(appComponent.provideAuthFragmentModel())
            SplashScreenActAuthFragSharedViewModel::class.java ->
                SplashScreenActAuthFragSharedViewModel(appComponent.provideSplashScreenModel())
            else -> throw IllegalArgumentException("Unknown viewModel class")
        }
        return viewModel as T
    }

}
