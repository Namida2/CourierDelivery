package com.example.courierdelivery.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.courierdelivery.R
import com.example.courierdelivery.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(binding!!.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController

//        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                Log.d("aaaaaaa", "onVerificatioknCompleted:$credential")
//                signInWithPhoneAuthCredential(credential)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                // This callback is invoked in an invalid request for verification is made,
//                // for instance if the the phone number format is not valid.
//                Log.w("aaaaaaa", "onVerificationFailed", e)
//
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                } else if (e is FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                }
//
//                // Show a message and update the UI
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                Log.d("aaaaaaa", "onCodeSent:$verificationId")
//                storedVerificationId = verificationId
//                resendToken = token
//            }
//        }
//        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
//            .setPhoneNumber("+79043775776")       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)

    }
//    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("aaaaaaa", "signInWithCredential:success")
//
//                    val user = task.result?.user
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    Log.w("aaaaaaa", "signInWithCredential:failure", task.exception)
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }
//                    // Update UI
//                }
//            }
//    }
}


