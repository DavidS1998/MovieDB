package aria.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import aria.moviedb.R
import aria.moviedb.model.Reviews
import aria.moviedb.network.ReviewsResults

class ReviewsAdapter() : RecyclerView.Adapter<ReviewsViewHolder>() {

    var data = listOf<ReviewsResults>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ReviewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.review_entry, parent, false)

        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val item = data[position]
        holder.authorView.text = item.author
        holder.contentView.text = item.content
    }
}

// TODO: Pass in binding and use that instead
class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val authorView: TextView = itemView.findViewById(R.id.author)
    val contentView: TextView = itemView.findViewById(R.id.content)
}