package di.modules

import com.example.courierdelivery.models.AuthFragmentModel
import com.example.courierdelivery.models.RouteMapsFragmentModel
import com.example.courierdelivery.models.SplashScreenModel
import com.example.courierdelivery.models.interfaces.AuthFragmentModelInterface
import com.example.courierdelivery.models.interfaces.RouteMapServicesInterface
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import com.example.courierdelivery.models.interfaces.SplashScreenModelInterface
import com.example.courierdelivery.models.services.RouteMapsServices
import com.example.courierdelivery.viewModels.fragments.RouteMapsFragmentViewModel
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ModelsModule {
    @Binds
    @Singleton
    fun bindSplashScreenModel(model: SplashScreenModel): SplashScreenModelInterface

    @Binds
    @Singleton
    fun bindAuthFragmentModel(model: AuthFragmentModel): AuthFragmentModelInterface

    @Binds
    @Singleton
    fun bindRouteMapsModel(model: RouteMapsFragmentModel): RouteMapsModelInterface

    @Binds
    @Singleton
    fun bindRouteMapsServices(model: RouteMapsServices): RouteMapServicesInterface
}