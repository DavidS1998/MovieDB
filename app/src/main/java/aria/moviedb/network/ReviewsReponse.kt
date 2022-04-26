package aria.moviedb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ReviewsReponse {
    @Json(name = "results")
    var results: List<ReviewsResults> = listOf()
}

class ReviewsResults {
    @Json(name = "content")
    var content: String = ""

    @Json(name = "author")
    var author: String = ""
}