package aria.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.Person.fromBundle
import androidx.navigation.fragment.findNavController
import aria.moviedb.databinding.FragmentMovieDetailsBinding
import aria.moviedb.model.Movie

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentMovieDetails : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movie = FragmentMovieDetailsArgs.fromBundle(requireArguments()).movie
        binding.movie = movie
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