package aria.moviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import aria.moviedb.databinding.MovieListItemBinding
import aria.moviedb.databinding.MovieListItemPostersBinding
import aria.moviedb.model.Movie

class MovieListAdapter(val movieListClickListener: MovieListClickListener) : ListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieListDiffCallback()) {
    class ViewHolder(private var binding: MovieListItemPostersBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, movieListClickListener: MovieListClickListener) {
            binding.movie = movie
            binding.clickListener = movieListClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemPostersBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position), movieListClickListener)
    }
}

class MovieListDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

class MovieListClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}