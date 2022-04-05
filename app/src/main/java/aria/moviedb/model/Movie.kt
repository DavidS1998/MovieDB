package aria.moviedb.model

data class Movie(
    val title: String,
    val posterPath: String,
    val bannerPath: String,
    val releaseDate: String,
    val overview: String
)