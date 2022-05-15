package com.ewind.newsapptest.view.main.favorite

import android.os.Bundle
import android.view.View
import com.ewind.newsapi.presentation.main.base.BaseFragment
import com.ewind.newsapptest.R
import com.ewind.newsapptest.databinding.FragmentFavoriteBinding


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)
    }
}