package uz.gita.rickandmorty.data.remote.responses

data class AllCharactersResponse(
    val info: Info? = null,
    val results: List<SingleCharacterResponse>? = null
)

data class Info(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: Any? = null
)

//@field:SerializedName("info")