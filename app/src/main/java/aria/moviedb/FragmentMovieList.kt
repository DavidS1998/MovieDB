package aria.moviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import aria.moviedb.database.Movies
import aria.moviedb.databinding.FragmentMovieListBinding
import aria.moviedb.databinding.MovieListItemBinding
import aria.moviedb.viewmodel.MovieListViewModel
import aria.moviedb.viewmodel.MovieListViewModelFactory
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentMovieList : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelFactory: MovieListViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieListBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        viewModelFactory = MovieListViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            movieList.forEach { movie ->
                val movieListItemBinding: MovieListItemBinding = DataBindingUtil.inflate(
                    inflater, R.layout.movie_list_item, container,false
                )
                movieListItemBinding.movie = movie
                movieListItemBinding.root.setOnClickListener {
                    this.findNavController()
                        .navigate(FragmentMovieListDirections.actionListToDetails(movie))
                }
                binding.movieListLinearLayout.addView(movieListItemBinding.root)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_popular_movies -> {
                Timber.i("Popular Movies Clicked")
                true
            }
            R.id.action_top_rated_movies -> {
                Timber.i("Top Rated Movies Clicked")
                true
            }
            R.id.action_saved_movies -> {
                Timber.i("Saved Movies Clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}