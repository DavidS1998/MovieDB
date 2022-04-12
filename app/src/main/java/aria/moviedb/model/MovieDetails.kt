package aria.moviedb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    var id: Long = 0L,
    var homepage: String,
    var imdb_id: String
) : Parcelable