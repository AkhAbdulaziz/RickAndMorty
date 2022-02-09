package uz.gita.rickandmorty.data.remote.responses

data class AllCharactersResponse(
    val info: Info? = null,
    val results: List<SingleCharacterResponse>? = null
)

data class Info(
    val next: String? = null,
    val pages: Int? = null,
    val prev: Any? = null,
    val count: Int? = null
)

//@field:SerializedName("info")