package com.example.courierdelivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.R
import com.example.courierdelivery.databinding.FragmentMapBinding

class MapFragment: Fragment(), OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        val supportMapFragment =
            childFragmentManager.findFragmentById(binding!!.map.id) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        return binding?.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(MarkerOptions()
            .position(LatLng(55.3333, 86.0833))
            .title("Marker"))
    }

}