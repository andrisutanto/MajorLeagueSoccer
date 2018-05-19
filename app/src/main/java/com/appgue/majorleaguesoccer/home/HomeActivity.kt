package com.appgue.majorleaguesoccer.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.R.id.favorites
import com.appgue.majorleaguesoccer.R.id.teams
import com.appgue.majorleaguesoccer.R.layout.activity_home
import com.appgue.majorleaguesoccer.favorites.FavoriteTeamsFragment
import com.appgue.majorleaguesoccer.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                teams -> {
                    loadTeamsFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        })
        bottom_navigation.selectedItemId = teams
    }


    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteTeamsFragment(), FavoriteTeamsFragment::class.simpleName)
                    .commit()
        }
    }
}

