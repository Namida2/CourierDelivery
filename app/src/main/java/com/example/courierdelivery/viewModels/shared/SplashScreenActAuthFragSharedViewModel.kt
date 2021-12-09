package com.example.courierdelivery.viewModels.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.SplashScreenModelInterface
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import entities.Event
import extensions.logD
import extensions.logE

sealed class SplashScreenVMStates {
    object Default : SplashScreenVMStates()
    object NotAuthorized : SplashScreenVMStates()
    object EmptyField : SplashScreenVMStates()
    object Validation : SplashScreenVMStates()
    object CodeSent : SplashScreenVMStates()
    object InvalidNumber : SplashScreenVMStates()
    object InvalidConfirmationCode : SplashScreenVMStates()
    class VerificationCompleted(val redential: PhoneAuthCredential) : SplashScreenVMStates()
}

class SplashScreenActAuthFragSharedViewModel(
    private val model: SplashScreenModelInterface,
) : ViewModel() {

    private var _state: MutableLiveData<SplashScreenVMStates> =
        MutableLiveData(SplashScreenVMStates.Default)
    val state: LiveData<SplashScreenVMStates> = _state

    private var _logInButtonEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val logInButtonEvent: LiveData<Event<String>> = _logInButtonEvent

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private var phoneNumber: String? = null
    private var confirmationCode: String? = null

    val verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            logD("onVerificationCompleted:$credential")
            _state.value = SplashScreenVMStates.VerificationCompleted(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            logE("onVerificationFailed: $e")
            if (e is FirebaseAuthInvalidCredentialsException) {
                _state.value = SplashScreenVMStates.InvalidNumber
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            logD("onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
            _state.value = SplashScreenVMStates.CodeSent
        }

    }

    fun checkCurrentUser() {
        val result: String? = model.getCurrentUser()
        if (result == null) {
            _state.value = SplashScreenVMStates.NotAuthorized
            return
        }
        logD("Reading the data by number here")
    }

    fun onNumberChanged(charSequence: CharSequence) {
        phoneNumber = charSequence.toString()
    }

    fun onCodeChanged(charSequence: CharSequence) {
        confirmationCode = charSequence.toString()
    }

    fun onLogInButtonClick() {
        if (phoneNumber == null) {
            _state.value = SplashScreenVMStates.EmptyField
            _state.value = SplashScreenVMStates.Default
            return
        }
        _logInButtonEvent.value = Event(phoneNumber!!)
        _state.value = SplashScreenVMStates.Validation
    }

    fun onConfirmButtonClick() {
        if (confirmationCode == null) {
            _state.value = SplashScreenVMStates.EmptyField
            _state.value = SplashScreenVMStates.Default
            return
        }
        // TODO: Add a state for this case
        storedVerificationId ?: return
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, confirmationCode!!)
        _state.value = SplashScreenVMStates.VerificationCompleted(credential)

    }

    fun invalidConfirmationCode() {
        _state.value = SplashScreenVMStates.InvalidConfirmationCode
    }


}