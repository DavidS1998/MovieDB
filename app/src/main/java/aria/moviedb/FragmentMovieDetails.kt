package aria.moviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import aria.moviedb.database.Details
import aria.moviedb.databinding.FragmentMovieDetailsBinding
import aria.moviedb.model.Movie
import aria.moviedb.model.MovieDetails


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentMovieDetails : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var currentDetails: MovieDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movie = FragmentMovieDetailsArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        // Initialize movie details database, and bind it
        currentDetails = Details().detailsList[movie.id.toInt()]
        binding.details = currentDetails

        // List genres at the bottom
        binding.Genres.text = currentDetails.genres.joinToString(separator = ", ")

        // Clicking on IMDB button starts an IMDB activity
        binding.IMDB.setOnClickListener {
            val imdbIntent: Intent =
                Uri.parse("https://www.imdb.com/title/" + currentDetails.imdb_id).let { url ->
                    Intent(Intent.ACTION_VIEW, url)
                }
            val chooser = Intent.createChooser(imdbIntent, "Open with")
            startActivity(chooser)
        }

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