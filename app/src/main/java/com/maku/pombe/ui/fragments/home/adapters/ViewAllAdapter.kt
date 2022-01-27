package com.maku.pombe.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maku.core.domain.recent.Drink
import com.maku.core.domain.recent.Recent
import com.maku.pombe.databinding.RecentViewItemLayoutBinding
import com.maku.core.utils.CocktailsDiffUtil

class ViewAllAdapter internal constructor(): RecyclerView.Adapter<ViewAllAdapter.ListViewHolder>(){

    private var cocktails = emptyList<Drink>()

    class ListViewHolder(private val binding: RecentViewItemLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun setRecentCocktailItem(result: Drink){
            binding.result = result
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecentViewItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentCocktail = cocktails[position]
        holder.setRecentCocktailItem(currentCocktail)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    fun setData(newData: Recent){
        val recipesDiffUtil =
            CocktailsDiffUtil(cocktails, newData.drinks)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        cocktails = newData.drinks
        diffUtilResult.dispatchUpdatesTo(this)
    }


}