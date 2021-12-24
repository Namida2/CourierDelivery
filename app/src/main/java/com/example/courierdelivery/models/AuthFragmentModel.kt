package com.example.courierdelivery.models

import android.content.SharedPreferences
import com.example.courierdelivery.models.interfaces.AuthFragmentModelInterface
import com.google.firebase.auth.FirebaseAuth
import constants.SharedPreferencesConst
import javax.inject.Inject

class AuthFragmentModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): AuthFragmentModelInterface {
    override fun saveUserNumber(number: String) {
        sharedPreferences.edit().putString(
            SharedPreferencesConst.PHONE_NUMBER, number
        ).apply()
    }
}