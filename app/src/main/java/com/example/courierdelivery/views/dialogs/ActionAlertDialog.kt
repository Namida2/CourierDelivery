package com.example.courierdelivery.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.courierdelivery.R
import com.example.courierdelivery.databinding.DialogActionBinding
import com.google.android.material.progressindicator.BaseProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ActionAlertDialog : DialogFragment() {
    private var accept: () -> Unit = {}
    private var title: String? = null
    private var text: String? = null
    private var dismissDelay = 100L

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context, R.style.myAlertDialogStyle)
        val binding = DialogActionBinding.inflate(layoutInflater)
        initBinding(binding)
        builder.setView(binding.root)
        return builder.create()
    }

    private fun initBinding(binding: DialogActionBinding) {
        with(binding) {
            if (this@ActionAlertDialog.text != null)
                title.text = this@ActionAlertDialog.title
            if (this@ActionAlertDialog.title != null)
                text.text = this@ActionAlertDialog.text
            cancel.setOnClickListener { dismiss() }
            accept.setOnClickListener {
                this@ActionAlertDialog.accept.invoke()
                CoroutineScope(IO).launch {
                    delay(dismissDelay)
                    withContext(Main) {
                        dismiss()
                    }
                }
            }
        }
    }

    fun initDialogData(title: String? = null, text: String? = null, action: () -> Unit) {
        this.title = title
        this.text = title
        this.accept = action
    }
}