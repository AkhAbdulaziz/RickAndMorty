package uz.gita.rickandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.rickandmorty.databinding.ItemPagingLoadingBinding
import uz.gita.rickandmorty.utils.visible

class PassengersLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PassengersLoadStateAdapter.PassengersLoadStateViewHolder>() {

    inner class PassengersLoadStateViewHolder(
        private val binding: ItemPagingLoadingBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textError.text = loadState.error.localizedMessage
            }
            binding.progressbar.visible(loadState is LoadState.Loading)
            binding.buttonRetry.visible(loadState is LoadState.Error)
            binding.textError.visible(loadState is LoadState.Error)
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(
        holder: PassengersLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = PassengersLoadStateViewHolder(
        ItemPagingLoadingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        retry
    )
}

