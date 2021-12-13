package com.example.courierdelivery.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.courierdelivery.R
import com.example.courierdelivery.adapters.RouteMapsAdapter
import com.example.courierdelivery.adapters.itemDecorations.RouteMapsItemDecorations
import com.example.courierdelivery.databinding.FragmentRouteMapsBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.RouteMapsFragmentViewModel
import com.example.courierdelivery.viewModels.fragments.RouteMapsVMStates
import extensions.Animations.prepareHide
import extensions.Animations.prepareShow
import extensions.appComponent
import extensions.createMessageDialog

class RouteMapsFragment: Fragment() {

    private var adapter = RouteMapsAdapter()
    private lateinit var binding: FragmentRouteMapsBinding
    private val viewModel: RouteMapsFragmentViewModel by activityViewModels { ViewModelFactory }
    private var smallMargin: Int? = null
    private var largeMargin: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent.inject(this)
        smallMargin = resources.getDimensionPixelSize(R.dimen.small_margin)
        largeMargin = resources.getDimensionPixelSize(R.dimen.large_margin)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        initBinding(inflater, container)
        observeViewModelStates()
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) {
        binding = FragmentRouteMapsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        with(binding) {
            routeMapsRecyclerView.adapter = adapter.also {
                it.onRouteMapClick = ::onRouteMapClick
            }
            routeMapsRecyclerView.addItemDecoration(
                RouteMapsItemDecorations(
                    smallMargin!!,
                    largeMargin!!
                )
            )
        }
    }

    private fun observeViewModelStates() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RouteMapsVMStates.ReadingData -> {
                    binding.progressIndicator.prepareShow().start()
                }
                is RouteMapsVMStates.ReadingWasFailure -> {
                    requireContext().createMessageDialog(it.message)
                        ?.show(parentFragmentManager, "")
                }
                is RouteMapsVMStates.DataIsEmpty -> {
                    requireContext().createMessageDialog(it.message)
                        ?.show(parentFragmentManager, "")
                }
                is RouteMapsVMStates.ReadingWasSuccessful -> {
                    binding.progressIndicator.prepareHide().start()
                    adapter.setRouteMaps(it.routeMaps)
                }
                else -> {}//Default
            }
        }
    }

    private fun onRouteMapClick(routeMapId: Int) {
        viewModel.setCurrentRouteMapInfo(routeMapId)
        val direction = RouteMapsFragmentDirections
            .actionRouteMapsFragmentToRouteMapsDetailFragment(routeMapId)
        findNavController().navigate(direction)
    }

}