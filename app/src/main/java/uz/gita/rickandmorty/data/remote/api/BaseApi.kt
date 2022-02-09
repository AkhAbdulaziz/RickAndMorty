package uz.gita.rickandmorty.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.rickandmorty.data.remote.responses.AllCharactersResponse

interface BaseApi {

    @GET("character")
    suspend fun getAllCharacters(): Response<AllCharactersResponse>

    @GET("character")
    suspend fun getAllCharactersOnPage(@Query("page") page: Int): Response<AllCharactersResponse>
}