package aria.moviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import aria.moviedb.database.MovieDatabase
import aria.moviedb.database.MovieDatabaseDao
import aria.moviedb.databinding.FragmentMovieDetailsBinding
import aria.moviedb.model.Movie
import aria.moviedb.model.MovieDetails
import aria.moviedb.viewmodel.MovieDetailsViewModel
import aria.moviedb.viewmodel.MovieListViewModel
import aria.moviedb.viewmodel.MovieListViewModelFactory


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentMovieDetails : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movie: Movie
    private lateinit var movieDatabaseDao: MovieDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movie = FragmentMovieDetailsArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        // Initialize the ViewModel
        val application = requireNotNull(this.activity).application
        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()
        viewModel = MovieDetailsViewModel(movieDatabaseDao, movie.id, application)
        binding.viewModel = viewModel

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            isFavorite?.let {
                when (isFavorite) {
                    true -> {
                        binding.saveToDBButtonView.visibility = View.GONE
                        binding.removeFromDBButtonView.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.saveToDBButtonView.visibility = View.VISIBLE
                        binding.removeFromDBButtonView.visibility = View.GONE
                    }
                }
            }

        }

        // Movie details
        viewModel.details.observe(viewLifecycleOwner) { details ->
            details?.let {
                binding.details = details

                // List genres at the bottom
//                binding.Genres.text = details.genres.joinToString(separator = ", ")

                // Clicking on IMDB button starts an IMDB activity
                binding.IMDB.setOnClickListener {
                    val imdbIntent: Intent =
                        Uri.parse("https://www.imdb.com/title/" + details.imdb_id).let { url ->
                            Intent(Intent.ACTION_VIEW, url)
                        }
                    val chooser = Intent.createChooser(imdbIntent, "Open with")
                    startActivity(chooser)
                }
            }
        }

        // Reviews
        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviews?.let {
                binding.reviews = reviews
            }
        }

//        binding.videoView.setVideoURI(Uri.parse("https://www.themoviedb.org/video/play?key=774825"))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.returnToList.setOnClickListener {
            findNavController().navigate(FragmentMovieDetailsDirections.actionDetailsToList())
        }

        binding.posterButton.setOnClickListener {
            findNavController().navigate(FragmentMovieDetailsDirections.actionFragmentMovieDetailsToFragmentFullPoster(movie))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}