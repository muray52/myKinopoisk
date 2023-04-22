package com.example.mykinopoisk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mykinopoisk.data.api.model.CountriesApiModel
import com.example.mykinopoisk.data.api.model.GenresApiModel

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
//    @ColumnInfo(name = "countries")
//    val countries: ArrayList<CountriesApiModel>,
//    @ColumnInfo(name = "genres")
//    var genres: ArrayList<GenresApiModel>,
    @ColumnInfo(name = "posterUrlPreview")
    val posterUrlPreview: String?,
    @ColumnInfo(name = "favoritesFlag")
    val favoritesFlag: Boolean
) {
    companion object {
        const val TABLE_NAME = "top_films"
    }
}