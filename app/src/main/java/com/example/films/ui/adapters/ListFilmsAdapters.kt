package com.example.films.ui.adapters

import android.content.Context
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.R.layout.*
import com.example.films.services.filmsDataClasses.filmsDataClasses

class ListFilmsAdapters(private val values: List<filmsDataClasses>,
                        private val listener: OnItemClickListener,
                        private val context: Context) :
    RecyclerView.Adapter<ListFilmsAdapters.MyViewHolder>()  {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = from(parent.context).inflate(fragment_list_films, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleFilm?.text = values[position].title

        Glide.with(context).load(R.drawable.duck).into(holder.logoFilm)
        holder.logoFilm.setImageResource(R.drawable.ic_launcher_foreground)
    }

    override fun getItemCount() = values.size

    inner class MyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var titleFilm: TextView? = null
        var logoFilm: ImageView = itemView.findViewById(R.id.film_logo_1)

        init {
            titleFilm = itemView.findViewById(R.id.title_film_1)

            itemView.setOnClickListener(this)

            logoFilm.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}