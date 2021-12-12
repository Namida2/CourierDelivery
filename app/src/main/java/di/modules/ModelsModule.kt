package di.modules

import com.example.courierdelivery.models.AuthFragmentModel
import com.example.courierdelivery.models.RouteMapsDetailModel
import com.example.courierdelivery.models.RouteMapsFragmentModel
import com.example.courierdelivery.models.SplashScreenModel
import com.example.courierdelivery.models.interfaces.*
import com.example.courierdelivery.models.services.RouteMapsService
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
    fun bindRouteMapsDetailModel(model: RouteMapsDetailModel): RouteMapsDetailModelInterface
}