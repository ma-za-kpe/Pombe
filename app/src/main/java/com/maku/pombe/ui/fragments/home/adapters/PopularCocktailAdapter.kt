package com.maku.pombe.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maku.core.domain.popular.Popular
import com.maku.pombe.databinding.PopularItemLayoutBinding
import com.maku.core.utils.CocktailsDiffUtil

class PopularCocktailAdapter internal constructor(
    private val openBottomSheet: (Any) -> Unit
)  : RecyclerView.Adapter<PopularCocktailAdapter.ListViewHolder>(){

    private var cocktails = emptyList<com.maku.core.domain.popular.Drink>()

    class ListViewHolder(private val binding: PopularItemLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun setPopularCocktailItem(result: com.maku.core.domain.popular.Drink, openBottomSheet: (Any) -> Unit){
            binding.result = result

            binding.popularDashboardCard.setOnClickListener {
                openBottomSheet(result)
            }

            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentCocktail = cocktails[position]
        holder.setPopularCocktailItem(currentCocktail, openBottomSheet)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    fun setData(newData: Popular){
        val recipesDiffUtil =
            CocktailsDiffUtil(cocktails, newData.drinks)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        cocktails = newData.drinks
        diffUtilResult.dispatchUpdatesTo(this)
    }


}