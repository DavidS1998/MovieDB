package aria.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.model.MovieDetails
import aria.moviedb.network.DetailsResponse
import kotlinx.coroutines.launch

class MovieDetailsViewModel(MovieID: Long, application: Application) :
    AndroidViewModel(application) {

    // Used to send into networking API to get specific movie details
    private val ID = MovieID

    private val _details = MutableLiveData<MovieDetails>()
    val details: LiveData<MovieDetails>
        get() {
            return _details
        }

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            try {
                // Temporary variable
                val detailsResponse: DetailsResponse =
                    TMDBApi.movieListRetrofitService.getDetails(ID.toString())

                // Combine all listed genres into one string
                val genres = detailsResponse.genres.joinToString { it.name }

                _details.postValue(MovieDetails(detailsResponse.id, detailsResponse.homepage, detailsResponse.imdb, genres))
            } catch (e: Exception) {
                _details.postValue(MovieDetails(0, "Error", "Error", "Error"))
            }
        }
    }
}