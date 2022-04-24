package aria.moviedb.network

import aria.moviedb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DetailsResponse {

    @Json(name = "id")
    var id: Long = 0

    @Json(name = "genres")
    var genres: List<Genres> = listOf()

    @Json(name = "homepage")
    var homepage: String = ""

    @Json(name = "imdb_id")
    var imdb: String = ""
}

class Genres {
    @Json(name = "id")
    var id: Long = 0

    @Json(name = "name")
    var name: String = ""
}