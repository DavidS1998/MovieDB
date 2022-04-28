package aria.moviedb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Videos(
    var site: String,
    var name: String,
    var key: String
) : Parcelable