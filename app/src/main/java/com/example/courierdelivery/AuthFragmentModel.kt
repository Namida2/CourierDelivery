package com.example.courierdelivery

import com.example.courierdelivery.models.interfaces.AuthFragmentModelInterface
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthFragmentModel @Inject constructor(
    private val auth: FirebaseAuth
): AuthFragmentModelInterface {
    override fun authorizeNumber(number: String) {
        TODO("Not yet implemented")
    }
}