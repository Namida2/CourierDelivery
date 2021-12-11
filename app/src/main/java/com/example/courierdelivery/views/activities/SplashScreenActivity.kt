package com.example.courierdelivery.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.courierdelivery.databinding.ActivitySplashScreenBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.activities.SplashScreenActAuthFragSharedViewModel
import com.example.courierdelivery.viewModels.activities.SplashScreenVMStates
import com.example.courierdelivery.views.fragments.SplashScreenFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private var navController: NavController? = null
    private val viewModel: SplashScreenActAuthFragSharedViewModel by viewModels { ViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        observeViewModelStates()
        viewModel.checkCurrentUser()
    }

    private fun observeViewModelStates() {
        viewModel.state.observe(this) {
            when (it) {
                is SplashScreenVMStates.NotAuthorized -> {
                    navigateTo(SplashScreenFragmentDirections.actionSplashScreenFragmentToAuthorisationFragment())
                }
                is SplashScreenVMStates.Authorized -> {
                    navigateTo(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainActivity())
                }
                else -> {} // DefaultState
            }
        }
    }

    private fun navigateTo(direction: NavDirections) {
        CoroutineScope(IO).launch {
            delay(1500)
            withContext(Main) {
                navController!!.navigate(
                    direction
                )
            }
        }
    }

}