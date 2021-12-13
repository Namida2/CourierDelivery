package com.example.courierdelivery.views.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.courierdelivery.R
import com.example.courierdelivery.databinding.DialogRouteItemDetailBinding
import entities.routeMaps.Client
import entities.routeMaps.Provider

object RouteItemDetailDialog : DialogFragment() {

    lateinit var client: Client
    lateinit var provider: Provider

    private const val orderIdDelimiter = "_"
    private const val orderNumber = "№П"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context, R.style.myAlertDialogStyle)
        val binding = DialogRouteItemDetailBinding.inflate(layoutInflater)
        if(!::client.isInitialized || !::provider.isInitialized) return builder.create()
        initBinding(binding)
        return builder.setView(binding.root).create()
    }

    @SuppressLint("SetTextI18n")
    private fun initBinding(binding: DialogRouteItemDetailBinding) {
        with(binding) {
            orderId.text = orderNumber + provider.id + orderIdDelimiter + client.id
            providerName.text = provider.name
            providerAddress.text = provider.address
            providerPhone.text = provider.phone
            providerCommentary.text = provider.commentary

            clientAddress.text = client.address
            clientPhone.text = client.phone
            clientCommentary.text = client.commentary
        }
    }


}