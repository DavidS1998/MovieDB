package aria.moviedb.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.database.MovieDatabase
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.database.MovieRepository
import aria.moviedb.model.Movie
import aria.moviedb.network.DataFetchStatus
import kotlinx.coroutines.launch


class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>>
        get() {
            return _favoriteMovies
        }

    private val _navigateToMovieData = MutableLiveData<Movie>()
    val navigateToMovieData: LiveData<Movie>
        get() {
            return _navigateToMovieData
        }

    val movieRepository = MovieRepository(MovieDatabase.getDatabase(application))
    val movies = movieRepository.movies

    init {
        _dataFetchStatus.postValue(DataFetchStatus.LOADING)
//        getTopMoviesFromRepository()
    }

    fun getTopMoviesFromRepository() {
        viewModelScope.launch {
            try {
                movieRepository.refreshMoviesTop()
                _dataFetchStatus.postValue(DataFetchStatus.DONE)
            } catch (e: Exception) {
                _dataFetchStatus.postValue(DataFetchStatus.ERROR)
            }
        }
    }

    fun getPopularMoviesFromRepository() {
        viewModelScope.launch {
            try {
                movieRepository.refreshMoviesPop()
                _dataFetchStatus.postValue(DataFetchStatus.DONE)
            } catch (e: Exception) {
                _dataFetchStatus.postValue(DataFetchStatus.ERROR)
            }
        }
    }

    fun getSavedMovies() {
        viewModelScope.launch {
            movieRepository.getSavedMovies()
            _dataFetchStatus.postValue(DataFetchStatus.DONE)
        }
    }

    fun refreshCurrent() {
        viewModelScope.launch {
            movieRepository.refreshCurrent()
            _dataFetchStatus.postValue(DataFetchStatus.DONE)
        }
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieData.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieData.value = null
    }
}