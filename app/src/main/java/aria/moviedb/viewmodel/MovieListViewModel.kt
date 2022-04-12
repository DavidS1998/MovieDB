package aria.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.database.Movies
import aria.moviedb.model.Movie
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) : AndroidViewModel(application) {


    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    init {
        _movies.postValue(Movies().movieList)
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
}