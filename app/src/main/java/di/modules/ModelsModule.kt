package di.modules

import com.example.courierdelivery.models.SplashScreenModel
import com.example.courierdelivery.models.interfaces.SplashScreenModelInterface
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ModelsModule {
    @Binds
    @Singleton
    fun bindSplashScreenModel(model: SplashScreenModel): SplashScreenModelInterface
}