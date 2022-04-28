import aria.moviedb.network.DetailsResponse
import aria.moviedb.network.MovieResponse
import aria.moviedb.network.ReviewsReponse
import aria.moviedb.network.VideosResponse
import aria.moviedb.utils.Constants
import aria.moviedb.utils.Secrets
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Add a httpclient logger for debugging purpose
 * object.
 */
fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

private val movieListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(getLoggerIntercepter())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.MOVIE_LIST_BASE_URL)
    .build()

interface TMDBApiService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Secrets.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = Secrets.API_KEY
    ): MovieResponse

    @GET("{movie_id}?api_key=${Secrets.API_KEY}")
    suspend fun getDetails(
        @Path("movie_id")
        movieId: String
    ): DetailsResponse

    @GET("{movie_id}/reviews?api_key=${Secrets.API_KEY}")
    suspend fun getReviews(
        @Path("movie_id")
        movieId: String
    ): ReviewsReponse

    @GET("{movie_id}/videos?api_key=${Secrets.API_KEY}")
    suspend fun getVideos(
        @Path("movie_id")
        movieId: String
    ): VideosResponse
}

object TMDBApi {
    val movieListRetrofitService: TMDBApiService by lazy {
        movieListRetrofit.create(TMDBApiService::class.java)
    }
}