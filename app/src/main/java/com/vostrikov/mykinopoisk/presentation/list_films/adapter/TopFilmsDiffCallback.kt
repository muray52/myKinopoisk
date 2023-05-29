package com.vostrikov.mykinopoisk.presentation.list_films.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity

class TopFilmsDiffCallback: DiffUtil.ItemCallback<TopFilmsEntity>() {
    override fun areItemsTheSame(oldItem: TopFilmsEntity, newItem: TopFilmsEntity): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: TopFilmsEntity, newItem: TopFilmsEntity): Boolean {
        return oldItem == newItem
    }

}