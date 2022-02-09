package uz.gita.rickandmorty.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.rickandmorty.R
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse
import uz.gita.rickandmorty.databinding.ScreenBaseBinding
import uz.gita.rickandmorty.presentation.adapter.RvAdapter
import uz.gita.rickandmorty.presentation.viewmodel.BaseScreenViewModel
import uz.gita.rickandmorty.presentation.viewmodel.impl.BaseScreenViewModelImpl
import uz.gita.rickandmorty.utils.scope
import uz.gita.rickandmorty.utils.showToast

@AndroidEntryPoint
class BaseScreen : Fragment(R.layout.screen_base) {
    private val binding by viewBinding(ScreenBaseBinding::bind)
    private val viewModel: BaseScreenViewModel by viewModels<BaseScreenViewModelImpl>()
    private var charactersList = ArrayList<SingleCharacterResponse>()
    private val adapter by lazy { RvAdapter(charactersList) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCharacters()

        viewModel.getAllCharactersLiveData.observe(viewLifecycleOwner, allCharactersObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private val allCharactersObserver = Observer<List<SingleCharacterResponse>> {
        charactersList.clear()
        charactersList.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }
}