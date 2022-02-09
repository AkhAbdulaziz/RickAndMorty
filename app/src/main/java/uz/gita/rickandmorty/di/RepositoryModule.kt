package uz.gita.rickandmorty.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.rickandmorty.domain.BaseRepository
import uz.gita.rickandmorty.domain.impl.BaseRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getBaseRepository(repository: BaseRepositoryImpl): BaseRepository
}