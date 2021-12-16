package com.example.courierdelivery.views.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.courierdelivery.databinding.FragmentMapBinding
import com.example.courierdelivery.models.services.MapService
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.MapFragmentViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import javax.inject.Inject


class MapFragment : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapFragmentViewModel by activityViewModels { ViewModelFactory }
    private var googleMap: GoogleMap? = null

    companion object {
        const val requestCode = 1
    }

    @Inject
    lateinit var mapService: MapService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        observeLocationAccessEvent()
        initGoogleMap()
        return binding.root
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
            .snippet("asdasdasdada")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            .title(placeMark.description))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    placeMark.latitude, placeMark.longitude
                ), 14f
            )
        )
        googleMap.addPolyline(
            PolylineOptions().add(
                LatLng(55.3496579, 86.09403399999999)
            ).add(
                LatLng(55.35036530000001, 86.0924012)
            ).add(
                LatLng(55.34711309999999, 86.0907912)
            ).add(
                LatLng(55.3496579, 86.09403399999999)
            )
        )

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
            if(locationAccess) enableMyLocation()
        }
    }

}