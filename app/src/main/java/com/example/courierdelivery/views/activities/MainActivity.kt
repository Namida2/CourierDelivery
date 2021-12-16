package com.example.courierdelivery.views.activities

import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import com.example.courierdelivery.views.fragments.MapFragment
import com.google.android.material.snackbar.Snackbar

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
            navController?.navigateUp()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MapFragment.requestCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED)) {
                    viewModel.setLocationAccess(true)
                } else {
                    Snackbar.make(binding.root,
                        "Permission denied.",
                        Snackbar.LENGTH_LONG).setAnchorView(
                        binding.bottomNavigation
                    ).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

}


