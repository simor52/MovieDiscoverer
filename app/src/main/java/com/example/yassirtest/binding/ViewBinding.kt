package com.example.yassirtest.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.yassirtest.R
import com.example.yassirtest.model.Movie
import com.example.yassirtest.model.MovieInfo
import com.example.yassirtest.util.getGenres
import com.example.yassirtest.util.getGenresString

object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        text?.let {
            Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    @BindingAdapter("paletteImage")
    fun bindLoadImagePalette(view: AppCompatImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.vector_image_place_holder)
            .error(R.drawable.vector_image_place_holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }


    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("onBackPressed")
    fun bindOnBackPressed(view: View, onBackPress: Boolean) {
        val context = view.context
        if (onBackPress && context is OnBackPressedDispatcherOwner) {
            view.setOnClickListener {
                context.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("genreIds")
    fun bindGenreIds(view: TextView, movie: Movie) {
        view.text = "${movie.getReleaseYear()} ${movie.getGenres()}"
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("genres")
    fun bindGenres(view: TextView, movie: MovieInfo?) {
        movie?.let {
            view.text = "${it.getReleaseYear()} ${it.getGenresString()}"
        }
    }
}
