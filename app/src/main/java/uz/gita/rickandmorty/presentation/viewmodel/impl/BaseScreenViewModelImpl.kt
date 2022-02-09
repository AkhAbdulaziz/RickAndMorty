package uz.gita.rickandmorty.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse
import uz.gita.rickandmorty.domain.BaseRepository
import uz.gita.rickandmorty.presentation.viewmodel.BaseScreenViewModel
import javax.inject.Inject

@HiltViewModel
class BaseScreenViewModelImpl @Inject constructor(
    private val baseRepository: BaseRepository
) : ViewModel(), BaseScreenViewModel {

    override val getAllSavedCharactersLiveData = MutableLiveData<List<SingleCharacterResponse>>()
    override val getAllCharactersLiveData = MutableLiveData<List<SingleCharacterResponse>>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun getAllCharacters() {
        baseRepository.getAllCharacters().onEach {
            it?.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
            it?.onSuccess {
                getAllCharactersLiveData.value = it.results!!
            }
        }.launchIn(viewModelScope)
    }

    override fun getAllCharactersPagingData(): Flow<PagingData<SingleCharacterResponse>> =
        baseRepository.getAllCharactersByPage(viewModelScope)

    override fun addCharacterToLocal(character: SingleCharacterResponse) {
        baseRepository.addCharacterToLocal(character)
    }

    override fun getSavedCharacters() {
        getAllCharactersLiveData.value = baseRepository.savedCharactersList
    }
}