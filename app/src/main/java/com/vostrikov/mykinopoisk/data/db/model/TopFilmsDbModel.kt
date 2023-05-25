package com.vostrikov.mykinopoisk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TopFilmsDbModel.TABLE_NAME)
data class TopFilmsDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "filmId")
    val filmId: Int,
    @ColumnInfo(name = "nameRu")
    val nameRu: String?,
    @ColumnInfo(name = "year")
    val year: String?,
    @ColumnInfo(name = "genreAndYear")
    val genreAndYear: String?,
    @ColumnInfo(name = "posterUrlPreview")
    val posterUrlPreview: String?,
    @ColumnInfo(name = "favoritesFlag")
    var favoritesFlag: Boolean
) {
    companion object {
        const val TABLE_NAME = "top_films"
    }
}