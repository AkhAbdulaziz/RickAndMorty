package uz.gita.rickandmorty.di;

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.rickandmorty.data.local.LocalStorage
import uz.gita.rickandmorty.data.remote.ApiClient
import uz.gita.rickandmorty.data.remote.api.BaseApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getBaseApi(): BaseApi = ApiClient.retrofit.create(BaseApi::class.java)

    @Provides
    @Singleton
    fun getLocalStorage() = LocalStorage()
}
