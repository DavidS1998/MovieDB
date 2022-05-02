package aria.moviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import aria.moviedb.model.DatabaseMovie
import aria.moviedb.model.Movie
import aria.moviedb.network.MovieResponse

@Dao
interface MovieDatabaseDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movies ORDER BY id ASC")
    suspend fun getAllMovies(): List<DatabaseMovie>

    // Does the entry exist in the local favorites database?
    @Query("SELECT EXISTS (SELECT * from movies WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean
}