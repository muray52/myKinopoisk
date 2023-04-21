package com.example.mykinopoisk.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mykinopoisk.data.db.model.FavoritesFilmDbModel

@Dao
interface FilmsDao {

    @Query("SELECT * FROM ${FavoritesFilmDbModel.TABLE_NAME}")
    fun getAll(): LiveData<MutableList<FavoritesFilmDbModel>>

    @Query("SELECT * FROM ${FavoritesFilmDbModel.TABLE_NAME} WHERE filmId IN (:filmIds)")
    fun getByIds(filmIds: IntArray): List<FavoritesFilmDbModel>

    @Insert(entity = FavoritesFilmDbModel::class, onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(row: FavoritesFilmDbModel)

    //    @Delete(entity = TableStructureOfFavorites::class)
//    fun deleteById(row: TableStructureOfFavorites)
    @Query("DELETE FROM ${FavoritesFilmDbModel.TABLE_NAME} WHERE filmId = :filmId ")
    suspend fun deleteById(filmId: Int)

    @Query("DELETE FROM ${FavoritesFilmDbModel.TABLE_NAME}")
    suspend fun deleteAll()
}