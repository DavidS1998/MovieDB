package aria.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.model.Movie
import aria.moviedb.model.MovieDetails
import aria.moviedb.model.Reviews
import aria.moviedb.network.DetailsResponse
import aria.moviedb.network.ReviewsReponse
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieDatabaseDao: MovieDatabaseDao, MovieID: Long, application: Application) :
    AndroidViewModel(application) {

    // Used to send into networking API to get specific movie details
    private val ID = MovieID

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite        }

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
        setIsFavorite(MovieID)
    }

    // Saving to cache /////////////////////////////////////////////////////////////////////////////
    private fun setIsFavorite(MovieID: Long) {
        viewModelScope.launch {
            _isFavorite.value = movieDatabaseDao.isFavorite(MovieID)
        }
    }

    fun onAddToDBButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.insert(movie)
            setIsFavorite(movie.id)
        }
    }

    fun onRemoveFromDBButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.delete(movie)
            setIsFavorite(movie.id)
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

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