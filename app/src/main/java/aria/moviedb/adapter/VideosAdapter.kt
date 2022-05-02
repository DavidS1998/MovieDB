package aria.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import aria.moviedb.R
import aria.moviedb.model.Reviews
import aria.moviedb.model.Videos
import aria.moviedb.network.ReviewsResults
import aria.moviedb.network.VideosResults
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideosAdapter() : RecyclerView.Adapter<VideosViewHolder>() {

    var data = listOf<VideosResults>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): VideosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.trailer_entry, parent, false)

        return VideosViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val item = data[position]
        if (item.site != "YouTube") return

        holder.titleView.text = item.title
        holder.link.text = "https://www.youtube.com/watch?v=" + item.key

        // Initialize video through 3rd party library
        val youTubePlayer: YouTubePlayerCallback = object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(item.key, 0f)
            }
        }
        holder.youtubeView.getYouTubePlayerWhenReady(youTubePlayer)
    }
}

// TODO: Pass in binding and use that instead
class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.video_title_tv)
    val link: TextView = itemView.findViewById(R.id.youtube_link_tv)
    val youtubeView: YouTubePlayerView = itemView.findViewById(R.id.video_view)
}