package com.example.courierdelivery.models

import android.content.SharedPreferences
import com.example.courierdelivery.models.interfaces.SplashScreenModelInterface
import com.google.firebase.auth.FirebaseAuth
import constants.SharedPreferencesConst
import javax.inject.Inject

class SplashScreenModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences,
    //TODO: val remoteRepository:
) : SplashScreenModelInterface {
    private val defaultNumber = ""
    override fun getCurrentUser(): String? {
        if (auth.currentUser == null) return null
        val number = sharedPreferences.getString(SharedPreferencesConst.PHONE_NUMBER, defaultNumber)
        return if (number == defaultNumber) null else number
    }

}