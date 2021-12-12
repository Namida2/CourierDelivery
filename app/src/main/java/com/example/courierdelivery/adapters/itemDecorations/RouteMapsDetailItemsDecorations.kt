package com.example.courierdelivery.adapters.itemDecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.courierdelivery.adapters.AddressesAdapter

class RouteMapsDetailItemsDecorations(
    private val largeMargin: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter as AddressesAdapter
        when (parent.getChildAdapterPosition(view)) {
            0 ->
                outRect.top = largeMargin
            adapter.itemCount - 1 ->
                outRect.bottom = largeMargin
        }
    }
}
