package com.example.courierdelivery.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.courierdelivery.R
import com.example.courierdelivery.databinding.ActivitySplashScreenBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.shared.SplashScreenActAuthFragSharedViewModel
import com.example.courierdelivery.viewModels.shared.SplashScreenVMStates

class SplashScreenActivity : AppCompatActivity() {

    private var binding: ActivitySplashScreenBinding? = null
    private var navController: NavController? = null
    private val viewModel: SplashScreenActAuthFragSharedViewModel by viewModels { ViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding!!.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        observeViewModelStates()
        viewModel.checkCurrentUser()
    }

    private fun observeViewModelStates() {
        viewModel.state.observe(this) {
            when (it) {
                is SplashScreenVMStates.NotAuthorized -> {
                    val a = navController.hashCode()
                    navController!!.navigate(
                        R.id.action_splashScreenFragment_to_authorisationFragment
                    )
                }
                else -> {} // DefaultState
            }
        }
    }

}