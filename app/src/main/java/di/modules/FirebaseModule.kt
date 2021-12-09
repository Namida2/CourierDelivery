package di.modules

import com.google.firebase.auth.FirebaseAuth
import constants.FirebaseConstantsConst.AUTH_LANGUAGE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance().also { it.setLanguageCode(AUTH_LANGUAGE) }
}