package com.example.courierdelivery.views.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.courierdelivery.databinding.FragmentMapBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.MapFragmentViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.maps.android.PolyUtil
import extensions.logD

class MapFragment : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapFragmentViewModel by activityViewModels { ViewModelFactory }
    private var googleMap: GoogleMap? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val requestCode = 1
        const val checkSettingsRequest = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        initGoogleMap()
        createLocationRequest()
        observeLocationAccessEvent()
        observeLocationUpdateEvent()
        observeNewDestinationEvent()
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun setCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            viewModel.lastLocation = location
            viewModel.getDirection()
        }
    }

    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                logD("$this: Task<LocationSettingsResponse>: failure")
                try {
                    exception.startResolutionForResult(requireActivity(),
                        checkSettingsRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
            viewModel.locationCallback, Looper.getMainLooper())
    }

    private fun initGoogleMap() {
        val supportMapFragment =
            childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        enableMyLocation()
        val placeMark = viewModel.getPlaceMark() ?: return
        googleMap.addMarker(MarkerOptions()
            .position(LatLng(placeMark.latitude, placeMark.longitude))
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            .title(placeMark.description))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    placeMark.latitude, placeMark.longitude
                ), 14f
            )
        )
        setCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap?.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(ACCESS_FINE_LOCATION),
                requestCode)
        }
    }

    private fun observeLocationAccessEvent() {
        viewModel.locationAccessEvent.observe(viewLifecycleOwner) {
            val locationAccess = it.getData() ?: return@observe
            if (locationAccess) enableMyLocation()
        }
    }

    private fun observeLocationUpdateEvent() {
        viewModel.locationUpdateEvent.observe(viewLifecycleOwner) {
            val newLocation = it.getData() ?: return@observe
            //TODO: Update the polyline
        }
    }

    private fun observeNewDestinationEvent() {
        viewModel.newDirectionEvent.observe(viewLifecycleOwner) {
            val destination = it.getData() ?: return@observe
            googleMap?.addPolyline(
                PolylineOptions().addAll(
                    PolyUtil.decode(destination)
                )
            )
        }
    }

}