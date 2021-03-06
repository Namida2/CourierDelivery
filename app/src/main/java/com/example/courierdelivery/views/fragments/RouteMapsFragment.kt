package com.example.courierdelivery.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.courierdelivery.R
import com.example.courierdelivery.adapters.RouteMapsAdapter
import com.example.courierdelivery.adapters.itemDecorations.RouteMapsItemDecorations
import com.example.courierdelivery.databinding.FragmentRouteMapsBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.RouteMapsFragmentViewModel
import com.example.courierdelivery.viewModels.fragments.RouteMapsVMStates
import com.example.courierdelivery.views.activities.MainActivity
import com.example.courierdelivery.views.dialogs.ActionAlertDialog
import com.example.courierdelivery.views.dialogs.RouteMapMenuDialog
import extensions.Animations.prepareHide
import extensions.Animations.prepareShow
import extensions.createMessageDialog

class RouteMapsFragment : Fragment() {

    private var adapter = RouteMapsAdapter()
    private lateinit var binding: FragmentRouteMapsBinding
    private val viewModel: RouteMapsFragmentViewModel by activityViewModels { ViewModelFactory }
    private var smallMargin: Int? = null
    private var largeMargin: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        observeOnRouteMapClickEvent()
        observeOnCurrentRouteMapEmptyEvent()
        observeOnCompletedRouteMapClickEvent()
        observeOnRouteMapRemovedEvent()
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
                it.onRouteMapClick = viewModel!!::onRouteMapClick
            }
            routeMapsRecyclerView.addItemDecoration(
                RouteMapsItemDecorations(
                    smallMargin!!,
                    largeMargin!!
                )
            )
            getRouteMapsInfo()
        }
    }

    private fun getRouteMapsInfo() {
        val routeMapsInfo = viewModel.getRouteMapsInfo()
        if (routeMapsInfo.isEmpty())
            binding.refreshListButton.prepareShow().start()
        else adapter.setRouteMaps(routeMapsInfo)
    }

    private fun observeViewModelStates() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RouteMapsVMStates.ReadingData -> {
                    binding.progressIndicator.prepareShow().start()
                }
                is RouteMapsVMStates.ReadingWasSuccessful -> {
                    hideViews()
                    adapter.setRouteMaps(it.routeMaps)
                    viewModel.resetState()
                }
                else -> {
                    if (it is RouteMapsVMStates.Default) return@observe
                    it.message?.let { message ->
                        requireContext().createMessageDialog(message)
                            ?.show(parentFragmentManager, "")
                    }
                    viewModel.resetState()
                }
            }
        }
    }

    private fun hideViews() {
        if (binding.refreshListButton.alpha != 0f) {
            binding.progressIndicator.prepareHide().start()
            binding.refreshListButton.prepareHide().start()
        }
    }

    private fun observeOnCurrentRouteMapEmptyEvent() {
        viewModel.onCurrentRouteMapEmpty.observe(viewLifecycleOwner) {
            val routeMapId = it.getData() ?: return@observe
            if (ActionAlertDialog.isAdded) return@observe
            ActionAlertDialog.initDialogData {
                viewModel.setCurrentRouteMapInfo(routeMapId)
                val direction = RouteMapsFragmentDirections
                    .actionRouteMapsFragmentToRouteMapsDetailFragment(routeMapId)
                (requireActivity() as MainActivity).navigateToDestination(direction)
            }
            ActionAlertDialog.show(parentFragmentManager, "")
        }
    }

    private fun observeOnRouteMapClickEvent() {
        viewModel.onRouteMapClickEvent.observe(viewLifecycleOwner) {
            val routeMapId = it.getData() ?: return@observe
            val direction = RouteMapsFragmentDirections
                .actionRouteMapsFragmentToRouteMapsDetailFragment(routeMapId)
            (requireActivity() as MainActivity).navigateToDestination(direction)
        }
    }

    private fun observeOnCompletedRouteMapClickEvent() {
        viewModel.onCompletedRouteMapClickEvent.observe(viewLifecycleOwner) {
            val routeMapId = it.getData() ?: return@observe
            if(RouteMapMenuDialog.isAdded) return@observe
            RouteMapMenuDialog.routeMapId = routeMapId
            RouteMapMenuDialog.show(parentFragmentManager, "")
        }
    }

    private fun observeOnRouteMapRemovedEvent() {
        viewModel.onRouteMapRemovedEvent.observe(viewLifecycleOwner) {
            val routeMaps = it.getData() ?: return@observe
            adapter.setRouteMaps(routeMaps)
            if(routeMaps.isEmpty())
                binding.refreshListButton.prepareShow().start()
        }
    }

}