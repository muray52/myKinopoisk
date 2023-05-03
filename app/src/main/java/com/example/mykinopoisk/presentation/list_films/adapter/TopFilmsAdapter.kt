package com.example.mykinopoisk.presentation.list_films.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mykinopoisk.databinding.TopFilmsItemBinding
import com.example.mykinopoisk.databinding.TopFilmsItemFavoritesBinding
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.squareup.picasso.Picasso

class TopFilmsAdapter() : ListAdapter<TopFilmsEntity, TopFilmsViewHolder>(TopFilmsDiffCallback()) {

    var onFilmItemClickListener: ((TopFilmsEntity) -> Unit)? = null

    var onFilmItemLongClickListener: ((TopFilmsEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_FAVORITE_FALSE -> TopFilmsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            VIEW_TYPE_FAVORITE_TRUE -> TopFilmsItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> throw RuntimeException("Error in ViewType $viewType")
        }

        return TopFilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val binding = holder.binding
        val filmsItem = getItem(position)

        when (binding) {
            is TopFilmsItemBinding -> {
                binding.tvNameRu.text = filmsItem.nameRu
                binding.tvGenreAndYear.text = filmsItem.genreAndYear
                Picasso.get().load(filmsItem.posterUrlPreview)
                    .into(binding.ivIconMovie)
            }
            is TopFilmsItemFavoritesBinding -> {
                binding.tvNameRu.text = filmsItem.nameRu
                binding.tvGenreAndYear.text = filmsItem.genreAndYear
                Picasso.get().load(filmsItem.posterUrlPreview)
                    .into(binding.ivIconMovie)
            }
        }

        holder.itemView.setOnClickListener {
            onFilmItemClickListener?.invoke(filmsItem)
        }
        holder.itemView.setOnLongClickListener {
            onFilmItemLongClickListener?.invoke(filmsItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int =
        if(getItem(position).favoritesFlag) VIEW_TYPE_FAVORITE_TRUE
        else VIEW_TYPE_FAVORITE_FALSE

    companion object {
        const val VIEW_TYPE_FAVORITE_FALSE = 1
        const val VIEW_TYPE_FAVORITE_TRUE = 2
    }
}