package com.example.mykinopoisk.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykinopoisk.R

class TopFilmsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var nameRu: TextView = itemView.findViewById(R.id.tvNameRu)
    var genreAndYear: TextView = itemView.findViewById(R.id.tvGenreAndYear)
    var posterUrlPreview: ImageView = itemView.findViewById(R.id.ivIconMovie)
}