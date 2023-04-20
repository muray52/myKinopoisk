package com.example.mykinopoisk.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mykinopoisk.R
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.squareup.picasso.Picasso

class TopFilmsAdapter() : ListAdapter<TopFilmsEntity, TopFilmsViewHolder>(TopFilmsDiffCallback()) {

    var onFilmItemClickListener: ((TopFilmsEntity) -> Unit)? = null

    var onFilmItemLongClickListener: ((TopFilmsEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_FAVORITE_FALSE,0 -> R.layout.top_films_item
            VIEW_TYPE_FAVORITE_TRUE -> R.layout.top_films_item_favorites
            else -> throw RuntimeException("Error in ViewType $viewType")
        }
        val itemView = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return TopFilmsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val topFilmsItem = getItem(position)
        holder.nameRu.text = topFilmsItem.nameRu
        holder.genreAndYear.text = topFilmsItem.genreAndYear
        Picasso.get().load(topFilmsItem.posterUrlPreview)
            .into(holder.posterUrlPreview)

        holder.itemView.setOnClickListener {
            onFilmItemClickListener?.invoke(topFilmsItem)
        }
        holder.itemView.setOnLongClickListener {
            onFilmItemLongClickListener?.invoke(topFilmsItem)
            true
        }
    }

    companion object {
        const val VIEW_TYPE_FAVORITE_FALSE = 1
        const val VIEW_TYPE_FAVORITE_TRUE = 2
    }
}