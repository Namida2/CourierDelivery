package com.example.courierdelivery.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.courierdelivery.R
import com.example.courierdelivery.databinding.FragmentAuthorisationBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.shared.SplashScreenActAuthFragSharedViewModel
import com.example.courierdelivery.viewModels.shared.SplashScreenVMStates
import com.example.courierdelivery.views.dialogs.ProcessAlertDialog
import com.google.firebase.auth.*
import extensions.createMessageDialog
import extensions.logD
import extensions.logE
import tools.ErrorMessages.emptyFieldMessage
import tools.ErrorMessages.invalidConfirmationCodeMessage
import tools.ErrorMessages.invalidNumberMessage
import java.util.concurrent.TimeUnit

class AuthorisationFragment : Fragment() {

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var auth = FirebaseAuth.getInstance()

    private var binding: FragmentAuthorisationBinding? = null
    private val viewModel: SplashScreenActAuthFragSharedViewModel by activityViewModels { ViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAuthorisationBinding.inflate(inflater, container, false)
        binding!!.viewModel = viewModel
        callbacks = viewModel.verificationCallbacks
        observeLogInButtonEvent()
        observeViewModelStates()
        return binding!!.root
    }

    private fun observeViewModelStates() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SplashScreenVMStates.EmptyField -> {
                    requireContext().createMessageDialog(emptyFieldMessage)
                        ?.show(parentFragmentManager, "")
                }
                is SplashScreenVMStates.Validation -> {
                    if (ProcessAlertDialog.isAdded) return@observe
                    ProcessAlertDialog.show(parentFragmentManager, "")
                }
                is SplashScreenVMStates.CodeSent -> {
                    ProcessAlertDialog.onSuccess()
                }
                is SplashScreenVMStates.InvalidNumber -> {
                    ProcessAlertDialog.dismiss()
                    requireContext().createMessageDialog(invalidNumberMessage)
                        ?.show(parentFragmentManager, "")
                }
                is SplashScreenVMStates.VerificationCompleted -> {
                    signInWithPhoneAuthCredential(it.redential)
                }
                is SplashScreenVMStates.InvalidConfirmationCode -> {
                    requireContext().createMessageDialog(invalidConfirmationCodeMessage)
                        ?.show(parentFragmentManager, "")
                }
                else -> {} //DefaultState
            }
        }
    }

    private fun observeLogInButtonEvent() {
        viewModel.logInButtonEvent.observe(viewLifecycleOwner) {
            val phoneNumber = it.getData() ?: return@observe
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks!!)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    logD("signInWithCredential:success")
                    val user = task.result?.user

                    // TODO: Add the number to sharedPreferences
                    findNavController().navigate(R.id.mainActivity)
                } else {
                    logE("signInWithCredential:failure. Exception: ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        viewModel.invalidConfirmationCode()
                    }
                    // Update UI
                }
            }
    }


}