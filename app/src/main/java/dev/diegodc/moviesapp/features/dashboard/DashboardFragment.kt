package dev.diegodc.moviesapp.features.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.features.dashboard.screens.latest.LatestMoviesFragment
import dev.diegodc.moviesapp.features.dashboard.screens.popular.PopularMoviesFragment
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.UpcomingMoviesFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragments()
        initBottomNav()
    }

    private fun initBottomNav() {
        bottomNav_movies.setOnItemSelectedListener { item ->
            val showingFragment = getVisibleFragment()
            when (item.itemId){
                R.id.menu_item_dashboard_latest -> {
                    val fragment = childFragmentManager.findFragmentByTag("fragment_latest_movies")!!
                    childFragmentManager.beginTransaction().apply {
                        showingFragment?.let { hide(it) }
                        show(fragment)
                        commit()
                    }
                }
                R.id.menu_item_dashboard_popular -> {
                    val fragment = childFragmentManager.findFragmentByTag("fragment_popular_movies")!!
                    childFragmentManager.beginTransaction().apply {
                        showingFragment?.let { hide(it) }
                        show(fragment)
                        commit()
                    }
                }
                R.id.menu_item_dashboard_upcoming -> {
                    val fragment = childFragmentManager.findFragmentByTag("fragment_upcoming_movies")!!
                    childFragmentManager.beginTransaction().apply {
                        showingFragment?.let { hide(it) }
                        show(fragment)
                        commit()
                    }
                }
            }
            true
        }
    }

    private fun getVisibleFragment(): Fragment? {
        return childFragmentManager.fragments.firstOrNull { it.isVisible }
    }

    private fun initFragments(){
        val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
        childFragmentManager.fragments.forEach {
            fragmentTransaction.remove(it)
        }
        val latestFragment: Fragment = LatestMoviesFragment()
        val popularFragment: Fragment = PopularMoviesFragment()
        val upcomingFragment: Fragment = UpcomingMoviesFragment()

        upcomingFragment.let {
            fragmentTransaction.add(
                R.id.frameLayout_main,
                it,
                "fragment_upcoming_movies"
            ).hide(it)
        }

        popularFragment.let {
            fragmentTransaction.add(
                R.id.frameLayout_main,
                it,
                "fragment_popular_movies"
            ).hide(it)
        }

        latestFragment.let {
            fragmentTransaction.add(
                R.id.frameLayout_main,
                it,
                "fragment_latest_movies"
            ).commit()
        }

    }
}