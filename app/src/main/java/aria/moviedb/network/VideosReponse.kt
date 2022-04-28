package aria.moviedb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VideosResponse {
    @Json(name = "results")
    var results: List<VideosResults> = listOf()
}

class VideosResults {
    @Json(name = "site")
    var site: String = ""

    @Json(name = "name")
    var title: String = ""

    @Json(name = "key")
    var key: String = ""
}