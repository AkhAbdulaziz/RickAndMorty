package uz.gita.rickandmorty.domain

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.rickandmorty.data.remote.responses.AllCharactersResponse
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse

interface BaseRepository {

    fun getAllCharacters(): Flow<Result<AllCharactersResponse>>

    fun getAllCharactersByPage(scope: CoroutineScope): Flow<PagingData<SingleCharacterResponse>>

    val savedCharactersList: List<SingleCharacterResponse>

    fun addCharacterToLocal(character: SingleCharacterResponse)

    val charactersData: List<SingleCharacterResponse>

}