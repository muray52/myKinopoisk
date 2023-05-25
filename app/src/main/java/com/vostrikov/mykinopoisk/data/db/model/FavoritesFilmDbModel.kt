package com.vostrikov.mykinopoisk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavoritesFilmDbModel.TABLE_NAME)
data class FavoritesFilmDbModel(
    @PrimaryKey
    @ColumnInfo(name = "filmId")
    val filmId: Int,
    @ColumnInfo(name = "nameRu")
    val nameRu: String?,
    @ColumnInfo(name = "year")
    val year: String?,
    @ColumnInfo(name = "genreAndYear")
    val genreAndYear: String?,
    @ColumnInfo(name = "posterUrlPreview")
    val posterUrlPreview: String?
) {
    companion object {
        const val TABLE_NAME = "favorites"
    }
}