package com.maku.pombe.ui.fragments.home.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maku.pombe.R
import com.maku.pombe.data.models.latest.Latest
import com.maku.pombe.data.models.recent.Drink
import com.maku.pombe.databinding.RecentItemLayoutBinding
import com.maku.pombe.data.models.recent.Recent
import com.maku.pombe.databinding.LatestItemLayoutBinding
import com.maku.pombe.utils.CocktailsDiffUtil
import timber.log.Timber
import java.io.IOException
import java.net.URL

class LatestCocktailAdapter internal constructor(
    private val openBottomSheet: (Any) -> Unit
)  : RecyclerView.Adapter<LatestCocktailAdapter.ListViewHolder>(){

    private var cocktails = emptyList<com.maku.pombe.data.models.latest.Drink>()

    class ListViewHolder(private val binding: LatestItemLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun setLatestCocktailItem(result: com.maku.pombe.data.models.latest.Drink, openBottomSheet: (Any) -> Unit){
            binding.result = result

            binding.latestDashboardCard.setOnClickListener {
                openBottomSheet(result)
            }

            val thread = Thread {
                try {
                    createPaletteAsync(binding.latestDashboardCard, result.strDrinkThumb)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            thread.start()

            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LatestItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }

        private fun createPaletteAsync(
            card: CardView,
            strDrinkThumb: String
        ) {

            Palette.from(stringToBitmap(strDrinkThumb)!!).generate { palette ->

                palette?.vibrantSwatch
                palette?.darkVibrantSwatch
                palette?.lightVibrantSwatch
                palette?.mutedSwatch
                palette?.darkMutedSwatch
                palette?.lightMutedSwatch

                try{
                    card.setCardBackgroundColor(
                        palette?.lightVibrantSwatch?.rgb ?:
                        ContextCompat.getColor(card.context, R.color.cardBackgroundColor)
                    )
                } catch(e: NumberFormatException){ // handle your exception
                    Timber.e("error "+e.localizedMessage)
                }

            }
        }

        private fun stringToBitmap(string: String):Bitmap?{
            return try {
                val url = URL(string)
                BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                null
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentCocktail = cocktails[position]
        holder.setLatestCocktailItem(currentCocktail, openBottomSheet)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    fun setData(newData: Latest){
        val recipesDiffUtil =
            CocktailsDiffUtil(cocktails, newData.drinks)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        cocktails = newData.drinks
        diffUtilResult.dispatchUpdatesTo(this)
    }


}