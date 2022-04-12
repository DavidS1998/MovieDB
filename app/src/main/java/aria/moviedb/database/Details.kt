package aria.moviedb.database

import aria.moviedb.model.MovieDetails

class Details {
    val detailsList = mutableListOf<MovieDetails>()

    init {
        detailsList.add(
            MovieDetails(
                0,
                "https://www.warnerbros.com/movies/full-metal-jacket/",
                "tt0093058"
            )
        )
        detailsList.add(
            MovieDetails(
                1,
                "https://www.warnerbros.com/movies/poseidon/",
                "tt0409182"
            )
        )
        detailsList.add(
            MovieDetails(
                2,
                "http://www.loveactually.com/",
                "tt0314331"
            )
        )
        detailsList.add(
            MovieDetails(
                3,
                "http://www.scoopmovie.net/",
                "tt0457513"
            )
        )
        detailsList.add(
            MovieDetails(
                4,
                "http://www.sommervormbalkon.de/",
                "tt0477877"
            )
        )



        detailsList.add(
            MovieDetails(
                5,
                "https://www.warnerbros.com/movies/full-metal-jacket/",
                "tt0093058"
            )
        )
        detailsList.add(
            MovieDetails(
                6,
                "https://www.warnerbros.com/movies/poseidon/",
                "tt0409182"
            )
        )
        detailsList.add(
            MovieDetails(
                7,
                "http://www.loveactually.com/",
                "tt0314331"
            )
        )
        detailsList.add(
            MovieDetails(
                8,
                "http://www.scoopmovie.net/",
                "tt0457513"
            )
        )
        detailsList.add(
            MovieDetails(
                9,
                "http://www.sommervormbalkon.de/",
                "tt0477877"
            )
        )
    }
}