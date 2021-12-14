package com.example.courierdelivery.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.courierdelivery.databinding.ActivityMainBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.activities.MainActivityViewModel

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private val viewModel: MainActivityViewModel by viewModels { ViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        initNavController()
        observeNavigationEvent()
    }

    private fun observeNavigationEvent() {
        viewModel.navigationEvent.observe(this) {
            navController?.navigateUp();
        }
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController =
            navHostFragment.navController.also { it.addOnDestinationChangedListener(this) }
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController!!)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) {
        //
    }

}


