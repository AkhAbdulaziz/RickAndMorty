package uz.gita.rickandmorty.presentation.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.rickandmorty.R
import uz.gita.rickandmorty.databinding.ScreenBaseBinding
import uz.gita.rickandmorty.presentation.adapter.PassengersLoadStateAdapter
import uz.gita.rickandmorty.presentation.adapter.RvPagerAdapter
import uz.gita.rickandmorty.presentation.viewmodel.BaseScreenViewModel
import uz.gita.rickandmorty.presentation.viewmodel.impl.BaseScreenViewModelImpl
import uz.gita.rickandmorty.utils.scope
import uz.gita.rickandmorty.utils.showToast

@AndroidEntryPoint
class BaseScreen : Fragment(R.layout.screen_base) {
    private val binding by viewBinding(ScreenBaseBinding::bind)
    private val viewModel: BaseScreenViewModel by viewModels<BaseScreenViewModelImpl>()
    private val adapter by lazy { RvPagerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
//        refresh.isRefreshing = true
        refresh.setOnRefreshListener {
            viewModel.getAllCharactersPagingData().onEach {
                adapter.submitData(it)
                refresh.isRefreshing = false
            }.launchIn(lifecycleScope)
        }

        recyclerView.adapter = adapter
        adapter.withLoadStateHeaderAndFooter(
            PassengersLoadStateAdapter { adapter.retry() },
            PassengersLoadStateAdapter { adapter.retry() }
        )

        adapter.setItemClickListener { item ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://rickandmortyapi.com/")
            startActivity(intent)
        }

        adapter.setNewDataListener { newCharacter ->
            viewModel.addCharacterToLocal(newCharacter)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getAllCharactersPagingData().onEach {
            adapter.submitData(it)
            binding.refresh.isRefreshing = false
        }.launchIn(lifecycleScope)

        btnUp.setOnClickListener{
            recyclerView.scrollToPosition(0)
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }
}