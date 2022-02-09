package uz.gita.rickandmorty.domain.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.rickandmorty.data.datasource.HeroesDataSource
import uz.gita.rickandmorty.data.local.LocalStorage
import uz.gita.rickandmorty.data.remote.api.BaseApi
import uz.gita.rickandmorty.data.remote.responses.AllCharactersResponse
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse
import uz.gita.rickandmorty.domain.BaseRepository
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val localStorage: LocalStorage,
    private val baseApi: BaseApi
) : BaseRepository {
    private val config = PagingConfig(20)

    override val charactersData = ArrayList<SingleCharacterResponse>()

    override val savedCharactersList: List<SingleCharacterResponse>
        get(){
            val st = localStorage.savedCharacters
            val list = st.split("###")
            val result = ArrayList<SingleCharacterResponse>()
            charactersData.onEach {
                if (list.contains(it.id.toString())) {
                    result.add(it)
                }
            }
            return result
        }

    override fun getAllCharacters(): Flow<Result<AllCharactersResponse>> = flow {
        val response = baseApi.getAllCharacters()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllCharactersByPage(scope: CoroutineScope): Flow<PagingData<SingleCharacterResponse>> =
        Pager(config) {
            HeroesDataSource(baseApi)
        }.flow.cachedIn(scope)

    override fun addCharacterToLocal(character: SingleCharacterResponse) {
        if (!savedCharactersList.contains(character)) {
            val st = localStorage.savedCharacters
            localStorage.savedCharacters = "$st${character.id}###"
        }
    }

}