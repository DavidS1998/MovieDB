package aria.moviedb

import android.R
import android.content.ActivityNotFoundException
import android.content.Context
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


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentMovieDetails : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var details: Details

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movie = FragmentMovieDetailsArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        details = Details()
        binding.details = details.detailsList[movie.id.toInt()];
        var imdbID = details.detailsList[movie.id.toInt()].imdb_id

        binding.IMDB.setOnClickListener {
//            val imdbIntent: Intent = Uri.parse("https://www.imdb.com/title/tt0093058/").let { url ->
            val imdbIntent: Intent = Uri.parse("https://www.imdb.com/title/$imdbID").let { url ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}