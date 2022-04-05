package aria.moviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import aria.moviedb.database.Movies
import aria.moviedb.databinding.FragmentMovieListBinding
import aria.moviedb.databinding.MovieListItemBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        // Generate list of hardcoded movies, and add these to the binding
        val movies = Movies()

        // Create move list entries automatically
        movies.movieList.forEach { movie ->
            val movieListItemBinding: MovieListItemBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.movie_list_item,
                container,
                false)
            movieListItemBinding.movie = movie

            binding.movieListLinearLayout.addView(movieListItemBinding.root)
        }
        binding.movies = movies

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}