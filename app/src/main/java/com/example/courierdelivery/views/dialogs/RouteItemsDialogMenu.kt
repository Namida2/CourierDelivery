package com.example.courierdelivery.views.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.courierdelivery.databinding.DialogRouteItemMenuBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.dialogs.RouteItemDialogMenuVMStates
import com.example.courierdelivery.viewModels.dialogs.RouteItemDialogMenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus
import extensions.createMessageDialog

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
        initBinding(inflater, container)
        observeViewModelStates()
        observeShowDetailEvent()
        observeMarkOnTheMapEvent()
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) {
        binding = DialogRouteItemMenuBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        if(routeItem!!.status == RouteItemStatus.COMPLETED)
            with(binding) {
                markAsCompletedButton.isEnabled = false
                markOnTheMapButton.isEnabled = false
            }
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

    private fun observeMarkOnTheMapEvent() {
        viewModel.markOnTheMaEvent.observe(viewLifecycleOwner) {
            val event = it.getData() ?: return@observe
            this.dismiss()
        }
    }

    private fun observeViewModelStates() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RouteItemDialogMenuVMStates.PostingData ->
                    ProcessAlertDialog.show(parentFragmentManager, "")
                is RouteItemDialogMenuVMStates.OnPostingFailure -> {
                    ProcessAlertDialog.dismiss()
                    requireActivity().createMessageDialog(it.message)
                        ?.show(parentFragmentManager, "")
                }
                is RouteItemDialogMenuVMStates.OnPostingSuccessful -> {
                    ProcessAlertDialog.onSuccess()
                    viewModel.resetState()
                    this.dismiss()
                }
                else -> {}//Default
            }
        }
    }


}