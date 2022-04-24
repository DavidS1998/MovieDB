package aria.moviedb.viewmodel

import TMDBApi
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.database.Details
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.database.Movies
import aria.moviedb.model.Movie
import aria.moviedb.model.MovieDetails
import aria.moviedb.network.DataFetchStatus
import aria.moviedb.network.MovieResponse
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    private val _navigateToMovieData = MutableLiveData<Movie>()
    val navigateToMovieData: LiveData<Movie>
        get() {
            return _navigateToMovieData
        }

    init {
        _dataFetchStatus.postValue(DataFetchStatus.LOADING)
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                // Temporary variable
                val movieResponse: MovieResponse =
                    TMDBApi.movieListRetrofitService.getPopularMovies()
                _movies.postValue(movieResponse.results)
                _dataFetchStatus.postValue(DataFetchStatus.DONE)
            } catch (e: Exception) {
                _dataFetchStatus.postValue(DataFetchStatus.ERROR)
                _movies.postValue(listOf())
            }
        }
    }

    fun getTopMovies() {
        viewModelScope.launch {
            try {
                // Temporary variable
                val movieResponse: MovieResponse =
                    TMDBApi.movieListRetrofitService.getTopRatedMovies()
                _movies.postValue(movieResponse.results)
                _dataFetchStatus.postValue(DataFetchStatus.DONE)
            } catch (e: Exception) {
                _dataFetchStatus.postValue(DataFetchStatus.ERROR)
                _movies.postValue(listOf())
            }
        }
    }


    fun getSavedMovies() {
        viewModelScope.launch {
            _movies.postValue(movieDatabaseDao.getAllMovies())
        }
    }

    fun addMovie() {
        // Run on separate thread
        viewModelScope.launch {
            movies.value?.let { movieDatabaseDao.insert(it[0]) }
        }
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieData.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieData.value = null
    }
}