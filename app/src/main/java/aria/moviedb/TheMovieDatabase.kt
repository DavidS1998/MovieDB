package aria.moviedb

import android.app.Application
import timber.log.Timber

class TheMovieDatabase : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}