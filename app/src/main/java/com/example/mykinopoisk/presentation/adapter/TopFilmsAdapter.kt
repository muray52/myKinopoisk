package com.example.mykinopoisk.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mykinopoisk.R
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.squareup.picasso.Picasso

class TopFilmsAdapter() : ListAdapter<TopFilmsEntity, TopFilmsViewHolder>(TopFilmsDiffCallback()) {

//    private val layoutRow: Int = _layoutRow
//    private val listOfJSONObjects: List<TopFilmsEntity>

//    private val repo = FavoritesRepository(FavoritesDataBase.getDatabase(context).getDao())
//    private var favoritesList: List<TableStructureOfFavorites>? = repo.getAll()
//    private var favoritesListOfFilmId: MutableList<Int>? =
//        favoritesList?.map { it.filmId } as MutableList<Int>

    var onFilmItemClickListener: ((TopFilmsEntity)->Unit)? = null

    var onFilmItemLongClickListener: ((TopFilmsEntity)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.top_films_item, parent, false)
        return TopFilmsViewHolder(itemView)

//        //call new activity on click
//        itemView.setOnClickListener {
//            if (myViewHolder.adapterPosition != RecyclerView.NO_POSITION) {
//                val intent = Intent(parent.context, DetailedMovieDescription::class.java)
//                val filmId = listOfJSONObjects[myViewHolder.adapterPosition].getString("filmId")
//                intent.putExtra(KEY_FILMID, filmId)
//                parent.context.startActivity(intent)
//            }
//        }
//        itemView.setOnLongClickListener {
//            if (myViewHolder.adapterPosition != RecyclerView.NO_POSITION) {
//                val filmId =
//                    listOfJSONObjects[myViewHolder.adapterPosition].getString("filmId").toInt()
//                if (favoritesListOfFilmId?.contains(filmId) == true) {
//                    //repo.deleteById(filmId)
//                    favoritesListOfFilmId?.remove(filmId)
//                    it.setBackgroundColor(Color.WHITE)
//                } else {
//                    val row = TableStructureOfFavorites(
//                        filmId = listOfJSONObjects[myViewHolder.adapterPosition].getString("filmId")
//                            .toInt(),
//                        nameRu = listOfJSONObjects[myViewHolder.adapterPosition].getString("nameRu"),
//                        year = listOfJSONObjects[myViewHolder.adapterPosition].getString("year"),
//                        posterUrlPreview = Picasso.get()
//                            .load(listOfJSONObjects[myViewHolder.adapterPosition].getString("posterUrlPreview"))
//                            .toString()
//                    )
//                    //repo.insert(row)
//                    favoritesListOfFilmId?.add(filmId)
//                    it.setBackgroundColor(Color.GRAY)
//                }
//                println("repo = ")
//                repo.allRows?.forEach() { itRepo ->
//                    println(itRepo.toString())
//                }
//                true
//            } else false
//        }

    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val topFilmsItem = getItem(position)
        holder.nameRu.text = topFilmsItem.nameRu
        holder.genreAndYear.text = topFilmsItem.genreAndYear
        Picasso.get().load(topFilmsItem.posterUrlPreview)
            .into(holder.posterUrlPreview)
//            val filmId = listOfJSONObjects[position].getString("filmId").toInt()
//            if (favoritesListOfFilmId?.contains(filmId) == true) {
//                holder.itemView.setBackgroundColor(Color.GRAY)

        holder.itemView.setOnClickListener{
            onFilmItemClickListener?.invoke(topFilmsItem)
        }
        holder.itemView.setOnLongClickListener {
            onFilmItemLongClickListener?.invoke(topFilmsItem)
            true
        }
    }
}