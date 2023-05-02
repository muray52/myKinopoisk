package com.example.mykinopoisk.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mykinopoisk.data.db.model.FavoritesFilmDbModel
import com.example.mykinopoisk.data.db.model.TopFilmsDbModel
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import androidx.room.DeleteTable as DeleteTable1

@Dao
interface FilmsDao {

    //next methods for Favorites
    @Query("SELECT * FROM ${FavoritesFilmDbModel.TABLE_NAME}")
    fun getFavoritesAll(): LiveData<MutableList<FavoritesFilmDbModel>>

    @Query("SELECT * FROM ${FavoritesFilmDbModel.TABLE_NAME}")
    suspend fun getFavoritesAllNoLiveData(): List<FavoritesFilmDbModel>

    @Query("SELECT * FROM ${FavoritesFilmDbModel.TABLE_NAME} WHERE filmId IN (:filmIds)")
    fun getFavoritesByIds(filmIds: IntArray): List<FavoritesFilmDbModel>

    @Insert(entity = FavoritesFilmDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(row: FavoritesFilmDbModel)

    @Query("DELETE FROM ${FavoritesFilmDbModel.TABLE_NAME} WHERE filmId = :filmId ")
    suspend fun deleteFavoritesById(filmId: Int)

    @Query("DELETE FROM ${FavoritesFilmDbModel.TABLE_NAME}")
    suspend fun deleteFavoritesAll()


    //next methods for TopFilms
    @Query("SELECT * FROM ${TopFilmsDbModel.TABLE_NAME} ORDER BY id")
    fun getTopFilmsAll(): LiveData<MutableList<TopFilmsDbModel>>

    @Query("SELECT * FROM ${TopFilmsDbModel.TABLE_NAME} WHERE filmId IN (:filmIds)")
    fun getTopFilmsByIds(filmIds: IntArray): List<TopFilmsDbModel>

    @Insert(entity = TopFilmsDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopFilms(topFilms: List<TopFilmsDbModel>)

    @Update(entity = TopFilmsDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTopFilms(topFilms: TopFilmsDbModel)

    @Query("DELETE FROM ${TopFilmsDbModel.TABLE_NAME} WHERE filmId = :filmId ")
    suspend fun deleteTopFilmsById(filmId: Int)

    @Query("DELETE FROM ${TopFilmsDbModel.TABLE_NAME}")
    suspend fun deleteTopFilmsAll()
}