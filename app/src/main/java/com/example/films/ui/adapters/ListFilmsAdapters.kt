package com.example.films.ui.adapters

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.R.layout.fragment_list_films
import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses

class ListFilmsAdapters(
    private val listener: OnItemClickListener
) : ListAdapter<FilmsDataClasses, ListFilmsAdapters.MyViewHolder>(FilmsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = from(parent.context).inflate(fragment_list_films, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleFilm?.text = getItem(position).title

        Glide.with(holder.itemView.context).load(R.drawable.duck).into(holder.logoFilm)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var titleFilm: TextView? = null
        var logoFilm: ImageView = itemView.findViewById(R.id.film_logo_1)

        init {
            titleFilm = itemView.findViewById(R.id.title_film_1)

            itemView.setOnClickListener(this)

            logoFilm.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}

object FilmsDiffUtil : DiffUtil.ItemCallback<FilmsDataClasses>() {

    override fun areItemsTheSame(oldItem: FilmsDataClasses, newItem: FilmsDataClasses): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilmsDataClasses, newItem: FilmsDataClasses): Boolean =
        oldItem == newItem
}