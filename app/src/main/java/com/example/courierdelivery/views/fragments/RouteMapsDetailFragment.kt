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
import com.example.courierdelivery.adapters.RouteItemsAdapter
import com.example.courierdelivery.adapters.itemDecorations.RouteMapsDetailItemsDecorations
import com.example.courierdelivery.databinding.FragmentRouteMapsDetailBinding
import com.example.courierdelivery.viewModels.ViewModelFactory
import com.example.courierdelivery.viewModels.fragments.RouteMapsDetailViewModel
import com.example.courierdelivery.views.dialogs.RouteItemsMenuDialog
import entities.routeMaps.RouteItem

class RouteMapsDetailFragment : Fragment() {

    private lateinit var binding: FragmentRouteMapsDetailBinding
    private val viewModel: RouteMapsDetailViewModel by activityViewModels { ViewModelFactory }
    private val args: RouteMapsDetailFragmentArgs by navArgs()
    private var menuDialog: RouteItemsMenuDialog = RouteItemsMenuDialog()
    private var adapter: RouteItemsAdapter? = null

    private var largeMargin: Int? = null
    private var defaultColorStateList: ColorStateList? = null
    private var completedColorStateList: ColorStateList? = null
    private var selectedColorStateList: ColorStateList? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        largeMargin = resources.getDimensionPixelSize(R.dimen.large_margin)
        defaultColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.black)
        completedColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.gray)
        selectedColorStateList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        initBinding(inflater, container)
        observeRouteMapInfoChangedEvent()
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
        val routeMapInfo = viewModel.getRouteMapById(args.routeMapId)
        adapter = RouteItemsAdapter(
            routeMapInfo,
            defaultColorStateList!!,
            completedColorStateList!!,
            selectedColorStateList!!,
            ::onAddressClick
        )
        binding.addressesRecyclerView.adapter = adapter
        binding.addressesRecyclerView.itemAnimator = null
        adapter!!.submitList(routeMapInfo.routeMap.routeItems)

        binding.addressesRecyclerView.addItemDecoration(
            RouteMapsDetailItemsDecorations(largeMargin!!)
        )
    }

    private fun onAddressClick(routeItem: RouteItem) {
        if(menuDialog.isAdded) return
        menuDialog.routeItem = routeItem
        menuDialog.show(parentFragmentManager, "")
    }

    private fun observeRouteMapInfoChangedEvent() {
        viewModel.routeMapInfoChangedEvent.observe(viewLifecycleOwner) {
            val routeItems = it.getData() ?: return@observe
            adapter!!.submitList(routeItems)
        }
    }

}