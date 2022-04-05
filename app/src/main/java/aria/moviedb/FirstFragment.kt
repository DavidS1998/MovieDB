package aria.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import aria.moviedb.database.Movies
import aria.moviedb.databinding.FragmentFirstBinding
import aria.moviedb.utils.Constants
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val movies = Movies()
        binding.movies = movies

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        Glide
//            .with(this)
//            .load(Constants.POSTER_URL + Constants.POSTER_SIZE + Movies().movieList[0].posterPath)
//            .into(binding.movie1.posterImageView);

//        binding.movie1.titleTextView.text = movies.movieList[0].title
//        binding.movie2.titleTextView.text = movies.movieList[1].title
//        binding.movie3.titleTextView.text = movies.movieList[2].title
//        binding.movie4.titleTextView.text = movies.movieList[3].title
//        binding.movie5.titleTextView.text = movies.movieList[4].title

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}