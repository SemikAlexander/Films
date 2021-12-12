package com.example.films.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.databinding.FilmsRecyclerviewBinding
import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses

class ListFilmsAdapters(
    private val listener: OnItemClickListener
) : ListAdapter<FilmsDataClasses, ListFilmsAdapters.MyViewHolder>(FilmsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(FilmsRecyclerviewBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titleFilm1.text = getItem(position).title

        if(getItem(position).poster_path != null)
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500${getItem(position).poster_path}")
                .into(holder.binding.filmLogo1)
        else
            holder.binding.filmLogo1.setImageResource(R.drawable.ic_image_not_supported)
    }

    inner class MyViewHolder(
        val binding: FilmsRecyclerviewBinding
    ) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)

            binding.filmLogo1.setOnClickListener {
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