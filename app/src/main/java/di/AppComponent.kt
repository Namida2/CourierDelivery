package di

import android.content.SharedPreferences
import com.example.courierdelivery.models.RouteMapMenuDialogModel
import com.example.courierdelivery.models.SplashScreenModel
import com.example.courierdelivery.models.interfaces.*
import com.example.courierdelivery.views.fragments.MapFragment
import com.example.courierdelivery.views.fragments.RouteMapsFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import di.modules.FirebaseModule
import di.modules.ModelsModule
import retrofit.DirectionsService
import javax.inject.Singleton

@Singleton
@Component(modules = [ModelsModule::class, FirebaseModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance sharedPreferences: SharedPreferences,
            @BindsInstance directionsService: DirectionsService
        ): AppComponent
    }

    fun provideSplashScreenModel(): SplashScreenModel
    fun provideAuthFragmentModel(): AuthFragmentModelInterface
    fun provideRouteMapsModel(): RouteMapsModelInterface
    fun provideRouteMapsDetailModel(): RouteMapsDetailModelInterface
    fun provideRouteItemDialogMenuModel(): RouteItemMenuDialogModelInterface
    fun provideMapFragmentModel(): MapFragmentModelInterface
    fun provideMainActivityModel(): MainActivityModelInterface
    fun provideRouteMapMenuDialogModel(): RouteMapMenuDialogModel
}