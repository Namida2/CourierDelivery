package application

import android.app.Application
import com.example.courierdelivery.R
import com.example.courierdelivery.viewModels.ViewModelFactory
import di.AppComponent
import di.DaggerAppComponent

class MyApplication : Application() {
    val _appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            this.getSharedPreferences(resources.getString(
                R.string.sharedPreferencesName),
                MODE_PRIVATE
            )
        )

    }
    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.appComponent = _appComponent
    }
}