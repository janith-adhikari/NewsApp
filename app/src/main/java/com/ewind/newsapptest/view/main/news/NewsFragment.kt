package com.ewind.newsapptest.view.main.news

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewind.newsapi.presentation.main.base.BaseFragment
import com.ewind.newsapptest.R
import com.ewind.newsapptest.databinding.FragmentNewsBinding
import com.ewind.newsapptest.domain.model.Category
import com.ewind.newsapptest.domain.model.DArticles
import com.ewind.newsapptest.util.PaginationScrollListener
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ResourceState
import com.ewind.newsapptest.util.ext.*
import com.ewind.newsapptest.view.component.adapter.BreakingNewsAdapter
import com.ewind.newsapptest.view.component.adapter.CategoryAdapter
import com.ewind.newsapptest.view.component.adapter.NewsAdapter
import com.ewind.newsapptest.view.component.dialog.showFilter
import com.ewind.newsapptest.view.main.newsview.EXTRA_NEWS
import com.ewind.newsapptest.view.main.newsview.NewsDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news),
    CategoryAdapter.AdapterListener, NewsAdapter.AdapterListener,
    BreakingNewsAdapter.AdapterListener {

    private val newsViewModel by viewModel<NewsViewModel>()
    private val breakingNewsViewModel by viewModel<BreakingNewsViewModel>()

    private lateinit var adapterPref: CategoryAdapter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var breakingNewsAdapter: BreakingNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel.newsLiveDate.observe(this) { updateView(it) }
        newsViewModel.livedataPre.observe(this) { populate(it) }
        breakingNewsViewModel.newsliveDate.observe(this) { updateBreakingView(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        adapterPref = CategoryAdapter(mutableListOf())
        adapterPref.listener = this
        binding.rvCategory.adapter = adapterPref

        initRecyclerView()
        initSearchEditText()

        binding.pullRefresh.setOnRefreshListener {
            refreshData()
            getNews()
        }
        binding.btnFilter.setOnClickListener {
            requireActivity().showFilter(
                breakingNewsViewModel.country,
                breakingNewsViewModel.lang
            ) { country, lang ->
                breakingNewsViewModel.setGlob(country, lang)
                newsViewModel.setGlob(country, lang)
                refreshBrekingData()
                refreshData()
                getBreakingNews()
                getNews()
            }
        }

        getBreakingNews()
        getNews()
        newsViewModel.preferenceAll()
    }

    private fun initSearchEditText() {
        binding.cancelSearch.setOnClickListener {
            binding.cancelSearch.gone()
            binding.etSearch.text.clear()
            refreshData()
            newsViewModel.keyword = "all"
            newsViewModel.getNews()

        }
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.appbarLayoutRestaurant.setExpanded(false)
                v.hideKeyboard()
                performSearch()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initRecyclerView() {
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

        breakingNewsAdapter = BreakingNewsAdapter(mutableListOf())
        breakingNewsAdapter.listener = this
        binding.rvBreakingNews.adapter = breakingNewsAdapter
        binding.rvBreakingNews.addOnScrollListener(object :
            PaginationScrollListener(binding.rvBreakingNews.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                breakingNewsViewModel.currentPage++
                getBreakingNews()
            }

            override fun isLastPage(): Boolean {
                return if (breakingNewsViewModel.totalCount != null) {
                    newsAdapter.itemCount >= breakingNewsViewModel.totalCount!!
                } else {
                    false
                }
            }

            override fun isLoading(): Boolean {
                return breakingNewsViewModel.isLoading
            }
        })
    }

    private fun getNews() {
        context?.isOnline {
            newsViewModel.getNews()
        }
    }

    private fun getBreakingNews() {
        context?.isOnline {
            breakingNewsViewModel.getTopNews()
        }
    }

    private fun performSearch() {
        refreshData()
        newsViewModel.keyword = binding.etSearch.text.toString()
        newsViewModel.getNews()
        binding.cancelSearch.visible()
    }

    private fun refreshData() {
        newsViewModel.currentPage = 1
        newsAdapter.clearDate()
    }

    private fun refreshBrekingData() {
        breakingNewsViewModel.currentPage = 1
        breakingNewsAdapter.clearDate()
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

    private fun updateBreakingView(resource: Resource<MutableList<DArticles>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    it.data?.let { it1 ->
                        breakingNewsAdapter.addNews(it1)
                    }
                }
                ResourceState.ERROR -> {
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
        refreshBrekingData()
        category.key?.let {
            newsViewModel.keyword = category.key
            breakingNewsViewModel.category = if (category.key == "all") "general" else category.key
        }
        getNews()
        getBreakingNews()
    }

    override fun onNewsSelected(news: DArticles) {
        context?.startActivity<NewsDetailsActivity> {
            putExtra(EXTRA_NEWS, news)
        }
    }
}