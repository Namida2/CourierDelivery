package com.example.courierdelivery.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.courierdelivery.databinding.FragmentRouteMapsDetailBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.RouteMapsFragmentViewModel

class RouteMapsDetailFragment: Fragment() {

    private lateinit var binding: FragmentRouteMapsDetailBinding
    private val viewModel: RouteMapsFragmentViewModel by activityViewModels { ViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRouteMapsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}