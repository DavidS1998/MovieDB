package aria.moviedb.model

import android.os.Parcelable
import aria.moviedb.database.Details
import aria.moviedb.network.Genres
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    var id: Long = 0L,
    var homepage: String,
    var imdb_id: String,
    var genres: String
) : Parcelable