package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.AuthFragmentModelInterface
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import entities.ErrorMessage
import entities.Event
import extensions.logD
import extensions.logE
import entities.tools.ErrorMessages.invalidConfirmationCodeMessage
import entities.tools.ErrorMessages.invalidNumberMessage
import entities.tools.ErrorMessages.numberNotSentMessage
import entities.tools.ErrorMessages.quotaHasBeenExceededMessage

sealed class AuthFragmentVMStates {
    open val errorMessage: ErrorMessage? = null

    object Default : AuthFragmentVMStates()
    object EmptyField : AuthFragmentVMStates()
    object Validation : AuthFragmentVMStates()
    object CodeSent : AuthFragmentVMStates()
    class InvalidNumber(
        override val errorMessage: ErrorMessage? = invalidNumberMessage,
    ) : AuthFragmentVMStates()

    class InvalidConfirmationCode(
        override val errorMessage: ErrorMessage? = invalidConfirmationCodeMessage,
    ) : AuthFragmentVMStates()

    class NumberNotSent(
        override val errorMessage: ErrorMessage? = numberNotSentMessage,
    ) : AuthFragmentVMStates()

    class VerificationCompleted(
        val credential: PhoneAuthCredential,
    ) : AuthFragmentVMStates()

    class QuotaHasBeenExceeded(
        override val errorMessage: ErrorMessage? = quotaHasBeenExceededMessage,
    ) : AuthFragmentVMStates()
}

class AuthFragmentViewModel(
    private val model: AuthFragmentModelInterface,
) : ViewModel() {

    private var _state: MutableLiveData<AuthFragmentVMStates> =
        MutableLiveData(AuthFragmentVMStates.Default)
    val state: LiveData<AuthFragmentVMStates> = _state

    private var _logInButtonEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val logInButtonEvent: LiveData<Event<String>> = _logInButtonEvent

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private var phoneNumber: String? = null
    private var confirmationCode: String? = null

    val verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            logD("onVerificationCompleted:$credential")
            _state.value = AuthFragmentVMStates.VerificationCompleted(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            logE("onVerificationFailed: $e")
            if (e is FirebaseAuthInvalidCredentialsException) {
                _state.value = AuthFragmentVMStates.InvalidNumber()
            } else if (e is FirebaseTooManyRequestsException) {
                _state.value = AuthFragmentVMStates.QuotaHasBeenExceeded()
            }
            //TODO: Default error
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            logD("onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
            _state.value = AuthFragmentVMStates.CodeSent
        }

    }

    fun onNumberChanged(charSequence: CharSequence) {
        phoneNumber = charSequence.toString()
    }

    fun onCodeChanged(charSequence: CharSequence) {
        confirmationCode = charSequence.toString()
    }

    fun onLogInButtonClick() {
        if (phoneNumber == null || phoneNumber!!.isEmpty()) {
            _state.value = AuthFragmentVMStates.EmptyField
            _state.value = AuthFragmentVMStates.Default
            return
        }
        _logInButtonEvent.value = Event(phoneNumber!!)
        _state.value = AuthFragmentVMStates.Validation
    }

    fun onConfirmButtonClick() {
        if (confirmationCode == null || confirmationCode!!.isEmpty()) {
            _state.value = AuthFragmentVMStates.EmptyField
            _state.value = AuthFragmentVMStates.Default
            return
        }
        if (storedVerificationId == null) {
            _state.value = AuthFragmentVMStates.NumberNotSent()
            return
        }
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, confirmationCode!!)
        _state.value = AuthFragmentVMStates.VerificationCompleted(credential)

    }

    fun invalidConfirmationCode() {
        _state.value = AuthFragmentVMStates.InvalidConfirmationCode()
    }

    fun saveUserNumber(number: String) {
        model.saveUserNumber(number)
    }

}