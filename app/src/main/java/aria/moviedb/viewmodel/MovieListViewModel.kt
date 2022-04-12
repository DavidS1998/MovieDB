package aria.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aria.moviedb.database.Movies
import aria.moviedb.model.Movie

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    init {
        _movies.postValue(Movies().movieList)
    }
}