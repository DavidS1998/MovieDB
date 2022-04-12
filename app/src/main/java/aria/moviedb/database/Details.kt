package aria.moviedb.database

import aria.moviedb.model.MovieDetails

class Details {
    val detailsList = mutableListOf<MovieDetails>()

    init {
        detailsList.add(
            MovieDetails(
                0,
                "https://www.warnerbros.com/movies/full-metal-jacket/",
                "tt0093058",
                listOf("Drama", "Action", "Thriller")
            )
        )
        detailsList.add(
            MovieDetails(
                1,
                "https://www.warnerbros.com/movies/poseidon/",
                "tt0409182",
                listOf("Drama")
            )
        )
        detailsList.add(
            MovieDetails(
                2,
                "http://www.loveactually.com/",
                "tt0314331",
                listOf("Action")
            )
        )
        detailsList.add(
            MovieDetails(
                3,
                "http://www.scoopmovie.net/",
                "tt0457513",
                listOf("Comedy", "Family")
            )
        )
        detailsList.add(
            MovieDetails(
                4,
                "http://www.sommervormbalkon.de/",
                "tt0477877",
                listOf("Comedy", "Thriller")
            )
        )



        detailsList.add(
            MovieDetails(
                0,
                "https://www.warnerbros.com/movies/full-metal-jacket/",
                "tt0093058",
                listOf("Drama", "Action", "Thriller")
            )
        )
        detailsList.add(
            MovieDetails(
                1,
                "https://www.warnerbros.com/movies/poseidon/",
                "tt0409182",
                listOf("Drama")
            )
        )
        detailsList.add(
            MovieDetails(
                2,
                "http://www.loveactually.com/",
                "tt0314331",
                listOf("Action")
            )
        )
        detailsList.add(
            MovieDetails(
                3,
                "http://www.scoopmovie.net/",
                "tt0457513",
                listOf("Comedy", "Family")
            )
        )
        detailsList.add(
            MovieDetails(
                4,
                "http://www.sommervormbalkon.de/",
                "tt0477877",
                listOf("Comedy", "Thriller")
            )
        )
    }
}