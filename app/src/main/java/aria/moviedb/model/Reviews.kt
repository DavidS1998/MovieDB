package aria.moviedb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reviews(
    var author: String,
    var content: String
) : Parcelable