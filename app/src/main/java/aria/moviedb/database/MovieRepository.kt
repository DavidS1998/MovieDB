package aria.moviedb.database

import TMDBApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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

    // For refreshing when internet connection is restored
    suspend fun refreshCurrent() {
        try {
            if (currentList == 1) {
                refreshMoviesTop()
            } else if (currentList == 2) {
                refreshMoviesPop()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    suspend fun refreshMoviesTop() {
        withContext(Dispatchers.IO) {
            try {
                currentList = 1
                Timber.d("Refresh top called")
                val _movies = TMDBApi.movieListRetrofitService.getTopRatedMovies()
                database.movieCacheDatabaseDao().deleteAll()
                database.movieCacheDatabaseDao().insertAll(asDatabaseModel(_movies))
            } catch (e: Exception) {
                database.movieCacheDatabaseDao().deleteAll()
                throw e
            }
        }
    }

    suspend fun refreshMoviesPop() {
        withContext(Dispatchers.IO) {
            try {
                currentList = 2
                Timber.d("Refresh popular called")
                val _movies = TMDBApi.movieListRetrofitService.getPopularMovies()
                database.movieCacheDatabaseDao().deleteAll()
                database.movieCacheDatabaseDao().insertAll(asDatabaseModel(_movies))
            } catch (e: Exception) {
                database.movieCacheDatabaseDao().deleteAll()
                throw e
            }
        }
    }

    suspend fun getSavedMovies() {
        withContext(Dispatchers.IO) {
            currentList = 0
            Timber.d("Saved movies called")
            var favorites: List<DatabaseMovie> = database.movieDatabaseDao().getAllMovies()
            database.movieCacheDatabaseDao().deleteAll()
            database.movieCacheDatabaseDao().insertAll(favorites)
        }
    }
}