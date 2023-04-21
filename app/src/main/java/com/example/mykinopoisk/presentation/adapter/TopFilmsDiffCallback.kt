package com.example.mykinopoisk.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class TopFilmsDiffCallback: DiffUtil.ItemCallback<TopFilmsEntity>() {
    override fun areItemsTheSame(oldItem: TopFilmsEntity, newItem: TopFilmsEntity): Boolean {
        return oldItem.filmId == newItem.filmId && oldItem.favoritesFlag == newItem.favoritesFlag
    }

    override fun areContentsTheSame(oldItem: TopFilmsEntity, newItem: TopFilmsEntity): Boolean {
        return oldItem == newItem
    }

}