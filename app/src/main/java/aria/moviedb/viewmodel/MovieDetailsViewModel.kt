package aria.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.model.MovieDetails
import aria.moviedb.model.Reviews
import aria.moviedb.network.DetailsResponse
import aria.moviedb.network.ReviewsReponse
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

    private val _reviews = MutableLiveData<Reviews>()
    val reviews: LiveData<Reviews>
        get() {
            return _reviews
        }

    init {
        getMovieDetails()
        getReviews()
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

    fun getReviews() {
        viewModelScope.launch {
            try {
                // Temporary variable
                val reviewsResponse: ReviewsReponse =
                    TMDBApi.movieListRetrofitService.getReviews(ID.toString())

                // TODO: Send entire lists instead of just the first review
                _reviews.postValue(Reviews(reviewsResponse.results[0].author, reviewsResponse.results[0].content))
            } catch (e: Exception) {
                _reviews.postValue(Reviews("No reviews found", ""))
            }
        }
    }
}