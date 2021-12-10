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
import com.example.courierdelivery.viewModels.fragments.AuthFragmentVMStates
import com.example.courierdelivery.viewModels.fragments.AuthFragmentViewModel
import com.example.courierdelivery.views.dialogs.ProcessAlertDialog
import com.google.firebase.auth.*
import extensions.createMessageDialog
import extensions.logD
import extensions.logE
import tools.ErrorMessages.emptyFieldMessage
import java.util.concurrent.TimeUnit

class AuthorisationFragment : Fragment() {

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var auth = FirebaseAuth.getInstance()

    private var binding: FragmentAuthorisationBinding? = null
    private val viewModel: AuthFragmentViewModel by activityViewModels { ViewModelFactory }

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
                is AuthFragmentVMStates.EmptyField -> {
                    requireContext().createMessageDialog(emptyFieldMessage)
                        ?.show(parentFragmentManager, "")
                }
                is AuthFragmentVMStates.Validation -> {
                    if (ProcessAlertDialog.isAdded) return@observe
                    ProcessAlertDialog.show(parentFragmentManager, "")
                }
                is AuthFragmentVMStates.CodeSent -> {
                    ProcessAlertDialog.onSuccess()
                }
                is AuthFragmentVMStates.VerificationCompleted -> {
                    signInWithPhoneAuthCredential(it.credential)
                }
                else -> {
                    if(it is AuthFragmentVMStates.Default) return@observe
                    val message = it.errorMessage ?: return@observe
                    requireContext().createMessageDialog(message)
                        ?.show(parentFragmentManager, "")
                }
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
                    viewModel.saveUserNumber(user?.phoneNumber!!)
                    findNavController().navigate(
                        AuthorisationFragmentDirections.actionAuthorisationFragmentToMainActivity()
                    )
                } else {
                    logE("signInWithCredential:failure. Exception: ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        viewModel.invalidConfirmationCode()
                    }
                }
            }
    }

}