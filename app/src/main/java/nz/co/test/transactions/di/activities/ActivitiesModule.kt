package nz.co.test.transactions.di.activities

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import nz.co.test.transactions.features.home.HomeActivity

@Module
class ActivitiesModule {

    @Provides
    @IntoMap
    @ActivityClassKey(HomeActivity::class)
    fun providesHomeActivity(): Activity = HomeActivity()
}