package extensions

import android.content.Context
import androidx.fragment.app.DialogFragment
import application.MyApplication
import com.example.courierdelivery.views.dialogs.MessageAlertDialog
import di.AppComponent
import entities.ErrorMessage

val Context.appComponent: AppComponent
get() = when(this) {
    is MyApplication -> _appComponent
    else -> this.applicationContext.appComponent
}

fun Context.createMessageDialog(message: ErrorMessage): DialogFragment? =
    MessageAlertDialog.getNewInstance<Unit>(
        this.resources.getString(message.titleId),
        this.resources.getString(message.messageId)
    )