package aria.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.model.Reviews
import aria.moviedb.network.ReviewsReponse
import aria.moviedb.network.ReviewsResults
import kotlinx.coroutines.launch

class FullPosterViewModel(private val movieDatabaseDao: MovieDatabaseDao, MovieID: Long, application: Application) :
    AndroidViewModel(application) {

    // Used to send into networking API to get specific movie details
    private val ID = MovieID

    private val _reviews = MutableLiveData<List<ReviewsResults>>()
    val reviews: LiveData<List<ReviewsResults>>
        get() {
            return _reviews
        }

    init {
        getReviews()
    }

    fun getReviews() {
        viewModelScope.launch {
            try {
                // Temporary variable
                val reviewsResponse: ReviewsReponse =
                    TMDBApi.movieListRetrofitService.getReviews(ID.toString())

                _reviews.postValue(reviewsResponse.results)
            } catch (e: Exception) {
                _reviews.postValue(listOf())
            }
        }
    }
}