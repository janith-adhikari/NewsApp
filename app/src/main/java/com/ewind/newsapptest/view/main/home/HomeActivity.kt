package com.ewind.newsapptest.view.main.home

import android.os.Bundle
import com.ewind.newsapptest.di.injectFeature
import com.ewind.newsapptest.view.main.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : BaseActivity() {

    //lateinit var binding:

    override fun onCreate(savedInstanceState: Bundle?) {
        injectFeature()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)

        /*//val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_top_news, R.id.navigation_news, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
}
