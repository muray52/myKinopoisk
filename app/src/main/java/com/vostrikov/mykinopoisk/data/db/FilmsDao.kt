package com.vostrikov.mykinopoisk.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vostrikov.mykinopoisk.data.db.model.FavoritesFilmDbModel
import com.vostrikov.mykinopoisk.data.db.model.TopFilmsDbModel

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

    @Query("UPDATE ${TopFilmsDbModel.TABLE_NAME} SET favoritesFlag = :favoritesFlag WHERE filmId =:filmId")
    suspend fun updateTopFilms(filmId: Int, favoritesFlag: Boolean)

    @Query("DELETE FROM ${TopFilmsDbModel.TABLE_NAME} WHERE filmId = :filmId ")
    suspend fun deleteTopFilmsById(filmId: Int)

    @Query("DELETE FROM ${TopFilmsDbModel.TABLE_NAME}")
    suspend fun deleteTopFilmsAll()

    @Query("SELECT * FROM ${TopFilmsDbModel.TABLE_NAME} WHERE nameRu LIKE '%' || :mask || '%'")
    suspend fun searchTopFilmsByMask(mask: String): MutableList<TopFilmsDbModel>

    @Query("SELECT * FROM ${FavoritesFilmDbModel.TABLE_NAME} WHERE nameRu LIKE '%' || :mask || '%'")
    suspend fun searchFavoriteFilmsByMask(mask: String): MutableList<FavoritesFilmDbModel>
}