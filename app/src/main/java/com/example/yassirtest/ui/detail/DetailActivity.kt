package com.example.yassirtest.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.example.yassirtest.R
import com.example.yassirtest.databinding.ActivityDetailBinding
import com.example.yassirtest.model.Movie
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    @Inject
    lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory

    @VisibleForTesting
    internal val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(detailViewModelFactory, movieId)
    }

    private var movieId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //onTransformationEndContainerApplyParams()
        movieId = intent.getLongExtra(EXTRA_movie, 0)
        super.onCreate(savedInstanceState)
        binding {
            vm = viewModel
            activity = this@DetailActivity
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    fun navigateToImbdLink() {
        val linkIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://www.imdb.com/title/${viewModel.movieInfo?.imdbId}")
        }
        startActivity(linkIntent);
    }

    companion object {
        @VisibleForTesting
        internal const val EXTRA_movie = "EXTRA_movie"

        fun startActivity(/*transformationLayout: TransformationLayout,*/context: Context, movie: Movie)  {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_movie, movie.id)
            context.startActivity(i)
        }
    }
}