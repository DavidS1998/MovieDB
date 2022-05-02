package aria.moviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import aria.moviedb.database.Movies
import aria.moviedb.network.MovieResponse
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title: String,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "banner_path")
    @Json(name = "backdrop_path")
    var bannerPath: String,

    @ColumnInfo(name = "release_date")
    @Json(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview: String
) : Parcelable

@Entity(tableName = "cached_movies")
data class DatabaseMovie(
    @PrimaryKey
    var id: Long = 0L,
    var title: String,

    @ColumnInfo(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "banner_path")
    var bannerPath: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    var overview: String
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            bannerPath = it.bannerPath,
            releaseDate = it.releaseDate,
            overview = it.overview
        )
    }
}

fun asDatabaseModel(movieResponse: MovieResponse): List<DatabaseMovie> {
    return movieResponse.results.map {
        DatabaseMovie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            bannerPath = it.bannerPath,
            releaseDate = it.releaseDate,
            overview = it.overview
        )
    }
}


