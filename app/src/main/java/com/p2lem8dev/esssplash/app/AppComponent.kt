import android.app.Application
import com.p2lem8dev.esssplash.app.AppModule
import com.p2lem8dev.esssplash.home.HomeModule
import com.p2lem8dev.esssplash.home.HomeRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        Application::class
    ],
    modules = [
        AppModule::class,
        HomeModule::class
    ]
)
interface AppComponent {
    fun homeRepository(): HomeRepository
}
