package com.example.courierdelivery.views.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.courierdelivery.databinding.DialogRouteMapMenuBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.dialogs.RouteMapMenuDialogViewModel
import com.example.courierdelivery.views.activities.MainActivity
import com.example.courierdelivery.views.fragments.RouteMapsFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

object RouteMapMenuDialog: BottomSheetDialogFragment() {

    var routeMapId: Int? = null

    private lateinit var binding: DialogRouteMapMenuBinding
    private val viewModel: RouteMapMenuDialogViewModel by activityViewModels { ViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.routeMapId = routeMapId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogRouteMapMenuBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        observeOnOpenRouteMapEvent()
        observeOnRemoveRouteMapEvent()
        return binding.root
    }

    private fun observeOnOpenRouteMapEvent() {
        viewModel.onOpenRouteMapEvent.observe(viewLifecycleOwner) {
            val routeMapId = it.getData() ?: return@observe
            val direction = RouteMapsFragmentDirections
                .actionRouteMapsFragmentToRouteMapsDetailFragment(routeMapId)
            (requireActivity() as MainActivity).navigateToDestination(direction)
            this.dismiss()
        }
    }

    private fun observeOnRemoveRouteMapEvent() {
        viewModel.onRemoveRouteMapEvent.observe(viewLifecycleOwner) {
            val routeMapId = it.getData() ?: return@observe
            viewModel.removeRouteMap(routeMapId)
            this.dismiss()
        }
    }

}