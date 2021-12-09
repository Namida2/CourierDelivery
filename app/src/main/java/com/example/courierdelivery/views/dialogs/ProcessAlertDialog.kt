package com.example.courierdelivery.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.courierdelivery.R
import com.example.courierdelivery.databinding.DialogProcessBinding
import extensions.Animations.prepareHide
import extensions.Animations.prepareShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ProcessAlertDialog : DialogFragment() {

    private const val startDelay = 150L

    private var binding: DialogProcessBinding? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogProcessBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext(), R.style.myAlertDialogStyle)
        isCancelable = false
        return builder.setView(binding?.root)
            .create()
    }
    fun onSuccess() {
        binding?.loadingLinearLayout!!.prepareHide().start()
        binding?.successTextView!!.prepareShow(startDelay = startDelay).start()
        CoroutineScope(Dispatchers.Main).launch {
            delay(600)
            dismiss()
        }
    }
}