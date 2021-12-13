package com.example.courierdelivery.views.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.courierdelivery.databinding.DialogRouteItemMenuBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.dialogs.RouteItemDialogMenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import entities.routeMaps.RouteItem

class RouteItemsDialogMenu : BottomSheetDialogFragment() {

    var routeItem: RouteItem? = null
    private lateinit var binding: DialogRouteItemMenuBinding
    private val viewModel: RouteItemDialogMenuViewModel by viewModels { ViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.routeItem = routeItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogRouteItemMenuBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        observeShowDetailEvent()
        return binding.root
    }

    private fun observeShowDetailEvent() {
        viewModel.showDetailEvent.observe(viewLifecycleOwner) {
            val pair = it.getData() ?: return@observe
            if (RouteItemDetailDialog.isAdded) return@observe
            RouteItemDetailDialog.client = pair.first
            RouteItemDetailDialog.provider = pair.second
            RouteItemDetailDialog.show(parentFragmentManager, "")
        }
    }
}