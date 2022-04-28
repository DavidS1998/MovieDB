package aria.moviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import aria.moviedb.adapter.ReviewsAdapter
import aria.moviedb.adapter.VideosAdapter
import aria.moviedb.database.MovieDatabase
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.databinding.FragmentFullPosterBinding
import aria.moviedb.model.Movie
import aria.moviedb.model.MovieDetails
import aria.moviedb.model.Reviews
import aria.moviedb.network.DetailsResponse
import aria.moviedb.network.ReviewsReponse
import aria.moviedb.viewmodel.FullPosterViewModel
import aria.moviedb.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.launch


class FragmentFullPoster : Fragment() {
    private var _binding: FragmentFullPosterBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var viewModel: FullPosterViewModel
    private lateinit var movieDatabaseDao: MovieDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullPosterBinding.inflate(inflater, container, false)
        movie = FragmentFullPosterArgs.fromBundle(requireArguments()).movieposter
        binding.movie = movie

        // Initialize the ViewModel
        val application = requireNotNull(this.activity).application
        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()
        viewModel = FullPosterViewModel(movieDatabaseDao, movie.id, application)

        // Initialize Recycler adapter
        val reviewsAdapter = ReviewsAdapter()
        binding.reviewsList.adapter = reviewsAdapter
        val videosAdapter = VideosAdapter()
        binding.videosList.adapter = videosAdapter

        // Puts the data from the ViewModel into the RecyclerView
        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviews?.let {
                reviewsAdapter.data = reviews
            }
        }

        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            videos?.let {
                videosAdapter.data = videos
            }
        }

        return binding.root
    }

    // Back button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.returnButton.setOnClickListener {
            findNavController().navigate(FragmentFullPosterDirections.actionFragmentFullPosterToFragmentMovieDetails(movie))
        }
    }
}