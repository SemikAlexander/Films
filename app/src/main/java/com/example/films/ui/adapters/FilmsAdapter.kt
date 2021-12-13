package com.example.films.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.databinding.FilmsRecyclerviewBinding
import com.example.films.services.retrofit.filmsDataClasses.Film

class FilmsAdapter(
    private val onItemClick: (Film) -> Unit
) : PagingDataAdapter<Film, FilmsAdapter.MyViewHolder>(FilmsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(FilmsRecyclerviewBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    inner class MyViewHolder(private val binding: FilmsRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Film) = binding.run {

            itemView.setOnClickListener { onItemClick.invoke(item) }
            titleFilm1.text = item.title

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .dontAnimate()
                .into(filmLogo1)
        }
    }
}

object FilmsDiffUtil : DiffUtil.ItemCallback<Film>() {

    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem == newItem
}