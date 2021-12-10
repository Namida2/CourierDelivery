package di

import android.content.SharedPreferences
import com.example.courierdelivery.models.SplashScreenModel
import com.example.courierdelivery.models.interfaces.AuthFragmentModelInterface
import dagger.BindsInstance
import dagger.Component
import di.modules.FirebaseModule
import di.modules.ModelsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ModelsModule::class, FirebaseModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance sharedPreferences: SharedPreferences
        ): AppComponent
    }

    fun provideSplashScreenModel(): SplashScreenModel
    fun provideAuthFragmentModel(): AuthFragmentModelInterface
}