package aria.moviedb.database

import TMDBApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import aria.moviedb.model.DatabaseMovie
import aria.moviedb.model.Movie
import aria.moviedb.model.asDatabaseModel
import aria.moviedb.model.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepository(private val database: MovieDatabase) {

    val movies: LiveData<List<Movie>> =
        map(database.movieCacheDatabaseDao().getAllMovies()) { it.asDomainModel() }

    private var currentList: Int = 0

    suspend fun refreshMoviesTop() {
        withContext(Dispatchers.IO) {
            try {
                Timber.d("Refresh popular called")
                val _movies = TMDBApi.movieListRetrofitService.getTopRatedMovies()
                database.movieCacheDatabaseDao().deleteAll()
                database.movieCacheDatabaseDao().insertAll(asDatabaseModel(_movies))
                currentList = 1
            } catch (e: Exception) {
                // Shows offline icon
                if (currentList != 1) { database.movieCacheDatabaseDao().deleteAll() }
                throw e
            }
        }
    }

    suspend fun refreshMoviesPop() {
        withContext(Dispatchers.IO) {
            try {
                Timber.d("Refresh popular called")
                val _movies = TMDBApi.movieListRetrofitService.getPopularMovies()
                database.movieCacheDatabaseDao().deleteAll()
                database.movieCacheDatabaseDao().insertAll(asDatabaseModel(_movies))
                currentList = 2
            } catch (e: Exception) {
                // Shows offline icon
                if (currentList != 2) { database.movieCacheDatabaseDao().deleteAll() }
                throw e
            }
        }
    }

    suspend fun getSavedMovies() {
        withContext(Dispatchers.IO) {
            Timber.d("Saved movies called")
            var favorites: List<DatabaseMovie> = database.movieDatabaseDao().getAllMovies()
            database.movieCacheDatabaseDao().deleteAll()
            database.movieCacheDatabaseDao().insertAll(favorites)
        }
    }
}