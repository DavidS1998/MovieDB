package aria.moviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import aria.moviedb.model.DatabaseMovie
import aria.moviedb.model.Movie
import aria.moviedb.network.MovieResponse

@Dao
interface MovieCacheDatabaseDao {

    @Query("SELECT * from cached_movies ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cached_movies: List<DatabaseMovie>)

    @Query("DELETE from cached_movies")
    suspend fun deleteAll()
}