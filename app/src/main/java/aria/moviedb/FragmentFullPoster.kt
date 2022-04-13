package aria.moviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import aria.moviedb.databinding.FragmentFullPosterBinding
import aria.moviedb.model.Movie


class FragmentFullPoster : Fragment() {
    private var _binding: FragmentFullPosterBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullPosterBinding.inflate(inflater, container, false)
        movie = FragmentFullPosterArgs.fromBundle(requireArguments()).movieposter
        binding.movie = movie

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.returnButton.setOnClickListener {
            findNavController().navigate(FragmentFullPosterDirections.actionFragmentFullPosterToFragmentMovieDetails(movie))
        }
    }
}