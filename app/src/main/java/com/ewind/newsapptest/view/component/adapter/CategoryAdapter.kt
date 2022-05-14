package com.ewind.newsapptest.view.component.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewind.newsapptest.domain.model.Category
import com.ewind.newsapptest.R
import com.ewind.newsapptest.databinding.ItemCategoryBinding
import com.ewind.newsapptest.util.ext.getCompatColor
import com.ewind.newsapptest.util.ext.getCompatColorState
import com.ewind.newsapptest.util.ext.getInflate

class CategoryAdapter(val categoryList: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var listener: AdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(parent.getInflate(), parent, false))
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.run { onBind(position) }

    inner class ViewHolder(val view: ItemCategoryBinding) : RecyclerView.ViewHolder(view.root) {
        fun onBind(position: Int) {
            val category = categoryList[position]
            view.tvCategory.text = category.key
            if (category.selected) {
                view.tvCategory.setTextColor(itemView.context.getCompatColor(R.color.white))
                view.tvCategory.backgroundTintList =
                    itemView.context.getCompatColorState(R.color.red)
            } else {
                view.tvCategory.setTextColor(itemView.context.getCompatColor(R.color.white))
                view.tvCategory.backgroundTintList =
                    itemView.context.getCompatColorState(R.color.white)
            }

            view.tvCategory.setOnClickListener {
                listener?.onCategorySelected(categoryList[absoluteAdapterPosition])
                categoryList.firstOrNull { it.selected }?.selected = false
                categoryList[absoluteAdapterPosition].selected = true
                notifyDataSetChanged()
            }
        }
    }

    fun clearDate() {
        categoryList.clear()
        notifyDataSetChanged()
    }

    fun addCategory(list: MutableList<Category>) {
        categoryList.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onCategorySelected(category: Category)
    }
}