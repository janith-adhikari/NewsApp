package com.ewind.newsapi.presentation.main.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ewind.newsapptest.view.main.base.BaseActivity



abstract class BaseFragment<T : ViewBinding>(@LayoutRes val contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    var _binding: T? = null
    val binding get() = _binding!!

    lateinit var baseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        baseActivity = requireActivity() as BaseActivity
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}