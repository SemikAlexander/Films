package com.example.films.adapters

import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.R.layout.*
import com.example.films.filmsDataClasses.filmsDataClasses

class ListFilmsAdapters(private val values: List<filmsDataClasses>,
                        private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ListFilmsAdapters.MyViewHolder>()  {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFilmsAdapters.MyViewHolder {
        val itemView = from(parent.context).inflate(fragment_list_films, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListFilmsAdapters.MyViewHolder, position: Int) {
        holder.titleFilm?.text = "The Poseidon Adventure"
        holder.logoFilm.setImageResource(R.drawable.test)
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