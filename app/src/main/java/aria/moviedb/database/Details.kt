package aria.moviedb.database

import aria.moviedb.model.MovieDetails

class Details(id: Long, homepage: String, imdb: String, genres: String) {
    val details = MovieDetails(id, homepage, imdb, genres)
}