package application

import android.app.Application
import com.example.courierdelivery.R
import com.example.courierdelivery.viewModels.ViewModelFactory
import di.AppComponent
import di.DaggerAppComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.DirectionsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    val _appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            this.getSharedPreferences(resources.getString(
                R.string.sharedPreferencesName),
                MODE_PRIVATE
            ),
            getDirectionsService()
        )

    }

    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.appComponent = _appComponent
    }

    private fun getDirectionsService(): DirectionsService {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originHttpUrl = chain.request().url
                val url = originHttpUrl.newBuilder()
                    .addQueryParameter(
                        getString(R.string.GOOGLE_MAPS_KEY_PARAMETER),
                        getString(R.string.GOOGLE_API_KEY)).build()
                request.url(url)
                return@addInterceptor chain.proceed(request.build())
            }
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(getString(R.string.GOOGLE_MAPS_BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(DirectionsService::class.java)
    }
}