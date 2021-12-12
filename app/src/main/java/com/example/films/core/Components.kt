package com.example.films.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.films.ui.infoFilm.FilmInformationFragment
import com.example.films.ui.infoFilm.InfoFilmViewModelFactory
import com.example.films.ui.listFilms.InfoFilmViewModel
import com.example.films.ui.listFilms.ListFilmViewModelFactory
import com.example.films.ui.listFilms.ListFilmsFragment
import com.example.films.ui.listFilms.ListFilmsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Component(
    dependencies = [CoreModuleDependencies::class],
    modules = [LobbyModule::class]
)
interface LobbyComponent {

    fun inject(listFilmsFragment: ListFilmsFragment)
    fun inject(filmInformationFragment: FilmInformationFragment)

    @Component.Factory
    interface FactoryFragment {
        fun create(
            coreComponentDependencies: CoreModuleDependencies,
            @BindsInstance fragment: Fragment
        ): LobbyComponent
    }
}

@InstallIn(FragmentComponent::class)
@Module
class LobbyModule {

    @Provides
    fun provideListFilmsViewModel(
        fragment: Fragment,
        factory: ListFilmViewModelFactory
    ) = ViewModelProvider(fragment, factory).get(ListFilmsViewModel::class.java)

    @Provides
    fun provideFilmInfoViewModel(
        fragment: Fragment,
        factory: InfoFilmViewModelFactory
    ) = ViewModelProvider(fragment, factory).get(InfoFilmViewModel::class.java)

    @Provides
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}