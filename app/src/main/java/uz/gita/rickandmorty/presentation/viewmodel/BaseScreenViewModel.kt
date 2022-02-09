package uz.gita.rickandmorty.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse
import kotlinx.coroutines.flow.Flow

interface BaseScreenViewModel {

    val getAllCharactersLiveData: LiveData<List<SingleCharacterResponse>>
    val getAllSavedCharactersLiveData: LiveData<List<SingleCharacterResponse>>
    val errorMessageLiveData: LiveData<String>

    fun getAllCharacters()
    fun getAllCharactersPagingData() : Flow<PagingData<SingleCharacterResponse>>

    fun addCharacterToLocal(character: SingleCharacterResponse)

    fun getSavedCharacters()
}