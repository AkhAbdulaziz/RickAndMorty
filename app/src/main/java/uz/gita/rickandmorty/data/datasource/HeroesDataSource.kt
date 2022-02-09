package uz.gita.rickandmorty.data.datasource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.gita.rickandmorty.data.remote.api.BaseApi
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse
import uz.gita.rickandmorty.utils.timber

class HeroesDataSource(private val api: BaseApi) : PagingSource<Int, SingleCharacterResponse>() {

    override fun getRefreshKey(state: PagingState<Int, SingleCharacterResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SingleCharacterResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            var nextPageKey: Int? = null
            val response = api.getAllCharactersOnPage(nextPageNumber)

            timber(response.isSuccessful.toString())
            timber(response.body().toString())

            if (response.body()!!.info!!.next != null) {
                val uri = Uri.parse(response.body()!!.info!!.next)
                val nextPage = uri.getQueryParameter("page")
                nextPageKey = nextPage!!.toInt()
            }
            LoadResult.Page(
                data = response.body()!!.results!!,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = nextPageKey
            )
        } catch (e: Exception) {
            timber("e = $e")
            LoadResult.Error(Throwable("Ulanishda xatolik bo'ldi"))
        }
    }
}