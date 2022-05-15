package com.ewind.newsapptest.view.component.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewind.newsapptest.databinding.ItemBreakingNewsBinding
import com.ewind.newsapptest.domain.model.DArticles
import com.ewind.newsapptest.util.ext.getInflate
import com.ewind.newsapptest.util.ext.loadImageCornerRound

class BreakingNewsAdapter(val newsList: MutableList<DArticles>) :
    RecyclerView.Adapter<BreakingNewsAdapter.ViewHolder>() {

    var listener: AdapterListener? = null
    val animate = Animate()
    private var on_attach = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBreakingNewsBinding.inflate(parent.getInflate(), parent, false));
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.run {
            onBind(position)
            animate.setAnimation(on_attach, itemView, position)
        }

    inner class ViewHolder(val view: ItemBreakingNewsBinding) : RecyclerView.ViewHolder(view.root) {
        fun onBind(position: Int) {

            val news = newsList[position]

            view.ivNewsImage.loadImageCornerRound(news.urlToImage, 32)
            view.tvNewsAuthor.text = news.source?.name
            view.tvNewsTitle.text = news.title
            view.tvDescription.text = news.description

            itemView.setOnClickListener {
                listener?.onNewsSelected(newsList[absoluteAdapterPosition])
            }
        }
    }

    fun clearDate() {
        newsList.clear()
        notifyDataSetChanged()
    }

    fun addNews(list: MutableList<DArticles>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                on_attach = false
            }
        })
    }

    interface AdapterListener {
        fun onNewsSelected(news: DArticles)
    }
}