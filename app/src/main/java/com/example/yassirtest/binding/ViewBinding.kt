package com.example.yassirtest.binding

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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
    fun bindLoadImagePalette(view: AppCompatImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(view)
//            .listener(
//                GlidePalette.with(url)
//                    .use(BitmapPalette.Profile.MUTED_LIGHT)
//                    .intoCallBack { palette ->
//                        val rgb = palette?.dominantSwatch?.rgb
//                        if (rgb != null) {
//                            paletteCard.setCardBackgroundColor(rgb)
//                        }
//                    }.crossfade(true)
//            ).into(view)
    }

//    @JvmStatic
//    @BindingAdapter("paletteImage", "paletteView")
//    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String) {
//        val context = view.context
//        Glide.with(context)
//            .load(url)
//            .into(view)
////            .listener(
////                GlidePalette.with(url)
////                    .use(BitmapPalette.Profile.MUTED_LIGHT)
////                    .intoCallBack { palette ->
////                        val light = palette?.lightVibrantSwatch?.rgb
////                        val domain = palette?.dominantSwatch?.rgb
////                        if (domain != null) {
////                            if (light != null) {
////                                Rainbow(paletteView).palette {
////                                    +color(domain)
////                                    +color(light)
////                                }.background(orientation = RainbowOrientation.TOP_BOTTOM)
////                            } else {
////                                paletteView.setBackgroundColor(domain)
////                            }
////                            if (context is AppCompatActivity) {
////                                context.window.apply {
////                                    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
////                                    statusBarColor = domain
////                                }
////                            }
////                        }
////                    }.crossfade(true)
////            )
//    }

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
}
