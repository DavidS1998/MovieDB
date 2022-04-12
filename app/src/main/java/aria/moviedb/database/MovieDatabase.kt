package aria.moviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import aria.moviedb.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    // Annotates class to be a Room Database with a table (entity) of the Word class
    @Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
    public abstract class WordRoomDatabase : RoomDatabase() {
        abstract fun movieDatabaseDao(): MovieDatabaseDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: MovieDatabase? = null

            fun getDatabase(context: Context): MovieDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie_database"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
    }
}