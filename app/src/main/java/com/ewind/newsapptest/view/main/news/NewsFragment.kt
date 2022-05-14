package com.ewind.newsapi.presentation.main.news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewind.newsapi.domain.model.Category
import com.ewind.newsapi.domain.model.DArticles
import com.ewind.newsapi.presentation.main.base.BaseFragment
import com.ewind.newsapptest.view.main.newsview.EXTRA_NEWS
import com.ewind.newsapptest.view.main.newsview.NewsViewActivity
import com.ewind.newsapptest.R
import com.ewind.newsapptest.databinding.FragmentNewsBinding
import com.ewind.newsapptest.util.PaginationScrollListener
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ResourceState
import com.ewind.newsapptest.util.ext.isOnline
import com.ewind.newsapptest.util.ext.startActivity
import com.ewind.newsapptest.util.ext.startRefresh
import com.ewind.newsapptest.util.ext.stopRefresh
import com.ewind.newsapptest.view.component.adapter.CategoryAdapter
import com.ewind.newsapptest.view.component.adapter.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news),
    CategoryAdapter.AdapterListener, NewsAdapter.AdapterListener {

    private val newsViewModel by viewModel<NewsViewModel>()
    private lateinit var adapterPref: CategoryAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel.newsliveDate.observe(this, Observer { updateView(it) })
        newsViewModel.livedataPre.observe(this, Observer { populate(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        adapterPref = CategoryAdapter(mutableListOf())
        adapterPref.listener = this
        binding.rvCategory.adapter = adapterPref

        newsAdapter = NewsAdapter(mutableListOf())
        newsAdapter.listener = this
        binding.rvTopNews.adapter = newsAdapter
        binding.rvTopNews.addOnScrollListener(object :
            PaginationScrollListener(binding.rvTopNews.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                newsViewModel.currentPage++
                getNews()
            }

            override fun isLastPage(): Boolean {
                return if (newsViewModel.totalCount != null) {
                    newsAdapter.itemCount >= newsViewModel.totalCount!!
                } else {
                    false
                }
            }

            override fun isLoading(): Boolean {
                return newsViewModel.isLoading
            }

        })

        binding.pullRefresh.setOnRefreshListener {
            refreshData()
            getNews()
        }

        getNews()
        newsViewModel.preferenceAll()
    }

    private fun getNews() {
        context?.isOnline {
            newsViewModel.getNews()
        }
    }

    private fun refreshData() {
        newsViewModel.currentPage = 1
        newsAdapter.clearDate()
    }

    private fun updateView(resource: Resource<MutableList<DArticles>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                    binding.pullRefresh.startRefresh()
                }
                ResourceState.SUCCESS -> {
                    binding.pullRefresh.stopRefresh()
                    it.data?.let { it1 ->
                        newsAdapter.addNews(it1)
                    }
                }
                ResourceState.ERROR -> {
                    binding.pullRefresh.stopRefresh()
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun populate(resource: Resource<List<Category>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    binding.pullRefresh.stopRefresh()
                    it.data?.toMutableList()?.let { it1 ->
                        if (it1.isNotEmpty()) {
                            it1[0].selected = true
                        }
                        adapterPref.addCategory(it1)
                    }
                }
                ResourceState.ERROR -> {
                }
            }
        }
    }

    override fun onCategorySelected(category: Category) {
        refreshData()
        category.key?.let {
            newsViewModel.keyword = category.key
        }
        getNews()
    }

    override fun onNewsSelected(news: DArticles) {
        context?.startActivity<NewsViewActivity> {
            putExtra(EXTRA_NEWS, news)
        }
    }
}