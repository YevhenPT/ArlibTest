package com.sts.investpuzzle.ui.home


import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sts.investpuzzle.R
import com.sts.investpuzzle.base.BaseActivity
import com.sts.investpuzzle.databinding.ActivityHomeBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class HomeActivity :BaseActivity<HomeActivityViewModel,ActivityHomeBinding>() {

    override val progressView: View get() = viewBind.mProgressView.root
    override val errorView: View get() = viewBind.mErrorView.root
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding(ActivityHomeBinding.inflate(layoutInflater))
        withViewModel<HomeActivityViewModel> {  }
        setUp()
    }

    override fun onClick(v: View?) {

    }

    override fun setUp() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

    }
}