package aria.moviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("posterImageUrl")
fun bindPosterImage(imgView: ImageView, imgUrl: String) {
    imgUrl.let { posterPath ->
        Glide
            .with(imgView)
            .load(Constants.POSTER_URL + Constants.POSTER_IMAGE_WIDTH + posterPath)
            .into(imgView)
    }
}

@BindingAdapter("bannerImageUrl")
fun bindBannerImage(imgView: ImageView, imgUrl: String) {
    imgUrl.let { bannerPath ->
        Glide
            .with(imgView)
            .load(Constants.BANNER_URL + Constants.BANNER_IMAGE_WIDTH + bannerPath)
            .into(imgView)
    }
}