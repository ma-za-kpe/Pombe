package com.maku.pombe.bindingadapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.palette.graphics.Palette
import coil.load
import com.bumptech.glide.Glide
import com.maku.pombe.R
import com.maku.pombe.data.models.recent.Drink
import com.maku.pombe.ui.fragments.home.HomeFragmentDirections
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber

class RecentRowBinding {

    companion object{

        @BindingAdapter("onRecentCocktailOnClickListener")
        @JvmStatic
        fun onRecentCocktailOnClickListener(recentCocktailRowLayout: ConstraintLayout, drink: Drink) {
            Timber.d("CALLED %s", drink)

            recentCocktailRowLayout.setOnClickListener {
                try {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToRecentBottomSheetFragment(drink)
                    recentCocktailRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Timber.d(" ${e.message}")
                }
            }
        }

//        @BindingAdapter("loadImageFromUrl")
//        @JvmStatic
//        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
//            imageView.load(imageUrl) {
//                crossfade(0)
//            }
//        }

        @BindingAdapter("extractColorPallet")
        @JvmStatic
        // Generate palette asynchronously and use it on a different
        // thread using onGenerated()
        fun createPaletteAsync(bitmap: Bitmap, card: CardView, context: Context) {
            Palette.from(bitmap).generate { palette ->

                var color = 0

                palette?.vibrantSwatch
                palette?.darkVibrantSwatch
                palette?.lightVibrantSwatch
                palette?.mutedSwatch
                palette?.darkMutedSwatch
                palette?.lightMutedSwatch

                // Use generated instance
                card.setBackgroundColor(ContextCompat.getColor(context,   Integer.parseInt( palette?.lightVibrantSwatch.toString())))
            }
        }


        @JvmStatic
        @BindingAdapter("loadImageFromUrl")
        fun loadImageFromUrl(view: ImageView, url: String) { // This methods should not have any return type, = declaration would make it return that object declaration.
            Glide.with(view.context).load(url).placeholder(R.drawable.ic_error_placeholder).into(view)
        }
    }

}