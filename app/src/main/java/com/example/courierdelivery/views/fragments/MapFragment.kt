package com.example.courierdelivery.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null
    private val viewModel: MapFragmentViewModel by activityViewModels { ViewModelFactory }
    private var googleMap: GoogleMap? = null

    @Inject
    lateinit var mapService: MapService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        initGoogleMap()
        return binding?.root
    }

    private fun initGoogleMap() {
        val supportMapFragment =
            childFragmentManager.findFragmentById(binding!!.map.id) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val placeMark = viewModel.getPlaceMark() ?: return
        googleMap.addMarker(MarkerOptions()
            .position(LatLng(placeMark.latitude, placeMark.longitude))
            .title(placeMark.description))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    placeMark.latitude, placeMark.longitude
                ), 14f
            )
        )

    }

}