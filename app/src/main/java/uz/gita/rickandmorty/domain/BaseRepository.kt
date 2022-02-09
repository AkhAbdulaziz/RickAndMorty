package uz.gita.rickandmorty.domain

import uz.gita.rickandmorty.data.remote.responses.AllCharactersResponse
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    fun getAllCharacters() : Flow<Result<AllCharactersResponse>>


}