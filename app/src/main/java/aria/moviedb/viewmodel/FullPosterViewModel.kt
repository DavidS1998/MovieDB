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
import aria.moviedb.network.VideosResponse
import aria.moviedb.network.VideosResults
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

    private val _videos = MutableLiveData<List<VideosResults>>()
    val videos: LiveData<List<VideosResults>>
        get() {
            return _videos
        }

    init {
        getReviews()
        getVideos()
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

    fun getVideos() {
        viewModelScope.launch {
            try {
                // Temporary variable
                val videosResponse: VideosResponse =
                    TMDBApi.movieListRetrofitService.getVideos(ID.toString())

                _videos.postValue(videosResponse.results)
            } catch (e: Exception) {
                _videos.postValue(listOf())
            }
        }
    }
}