package com.example.courierdelivery.views.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.courierdelivery.R
import com.example.courierdelivery.adapters.AddressesAdapter
import com.example.courierdelivery.adapters.RouteMapsAdapter
import com.example.courierdelivery.adapters.itemDecorations.RouteMapsDetailItemsDecorations
import com.example.courierdelivery.databinding.FragmentRouteMapsDetailBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.RouteMapsDetailViewModel

class RouteMapsDetailFragment : Fragment() {

    private lateinit var binding: FragmentRouteMapsDetailBinding
    private val viewModel: RouteMapsDetailViewModel by activityViewModels { ViewModelFactory }
    private val args: RouteMapsDetailFragmentArgs by navArgs()
    private var largeMargin: Int? = null
    private var defaultColorStateList: ColorStateList? = null
    private var completedColorStateList: ColorStateList? = null
    private var selectedColorStateList: ColorStateList? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        largeMargin = resources.getDimensionPixelSize(R.dimen.large_margin)
        defaultColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.black)
        completedColorStateList = ContextCompat.getColorStateList(requireContext(), android.R.color.darker_gray)
        selectedColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        initBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view?.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) {
        binding = FragmentRouteMapsDetailBinding.inflate(inflater, container, false)
        binding.addressesRecyclerView.adapter = AddressesAdapter(
            viewModel.getRouteMapById(args.routeMapId),
            defaultColorStateList!!,
            completedColorStateList!!,
            selectedColorStateList!!
        )
        binding.addressesRecyclerView.addItemDecoration(
            RouteMapsDetailItemsDecorations(largeMargin!!)
        )
    }

}