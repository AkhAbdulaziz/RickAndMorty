package uz.gita.rickandmorty.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.rickandmorty.data.remote.responses.AllCharactersResponse
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse

interface BaseScreenViewModel {

    val getAllCharactersLiveData: LiveData<List<SingleCharacterResponse>>
    val errorMessageLiveData: LiveData<String>

    fun getAllCharacters()
}