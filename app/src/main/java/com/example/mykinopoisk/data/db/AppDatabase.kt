package com.example.mykinopoisk.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mykinopoisk.data.db.model.FavoritesFilmDbModel
import com.example.mykinopoisk.data.db.model.TopFilmsDbModel

@Database(entities = [FavoritesFilmDbModel::class, TopFilmsDbModel::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun filmsDao(): FilmsDao
}