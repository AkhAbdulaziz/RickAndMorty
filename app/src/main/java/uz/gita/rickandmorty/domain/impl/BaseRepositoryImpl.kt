package uz.gita.rickandmorty.domain.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.rickandmorty.data.remote.api.BaseApi
import uz.gita.rickandmorty.data.remote.responses.AllCharactersResponse
import uz.gita.rickandmorty.domain.BaseRepository
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val baseApi: BaseApi,

    ) : BaseRepository {
    private val gson = Gson()

    override fun getAllCharacters(): Flow<Result<AllCharactersResponse>> = flow {
        val response = baseApi.getAllCharacters()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        }
    }.flowOn(Dispatchers.IO)

}