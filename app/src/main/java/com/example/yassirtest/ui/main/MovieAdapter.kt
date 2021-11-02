package com.example.yassirtest.ui.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.yassirtest.R
import com.example.yassirtest.databinding.ItemMovieBinding
import com.example.yassirtest.model.Movie
import com.example.yassirtest.ui.detail.DetailActivity
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        parent.binding<ItemMovieBinding>(R.layout.item_movie).let(::MovieViewHolder)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindMovie(getItem(position))

    inner class MovieViewHolder constructor(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
                    DetailActivity.startActivity(it.context, getItem(position)!!)
            }
        }

        fun bindMovie(movie: Movie?) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}