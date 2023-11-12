package nz.co.test.transactions.di

import dagger.Component
import nz.co.test.transactions.di.activities.ActivitiesModule
import nz.co.test.transactions.di.network.NetworkModule
import nz.co.test.transactions.di.viewmodels.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ActivitiesModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {
    fun inject(appComponent: DaggerAppComponentFactory)
}