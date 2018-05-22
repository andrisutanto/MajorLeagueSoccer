package com.appgue.majorleaguesoccer.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.R.id.favoritesEvent
import com.appgue.majorleaguesoccer.R.id.prevmatch
import com.appgue.majorleaguesoccer.R.id.nextmatch
import com.appgue.majorleaguesoccer.R.layout.activity_home
import com.appgue.majorleaguesoccer.favorites.FavoriteEventFragment
import com.appgue.majorleaguesoccer.next.NextEventFragment
import com.appgue.majorleaguesoccer.previous.PrevEventFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                prevmatch -> {
                    loadPrevMatchFragment(savedInstanceState)
                }
                nextmatch -> {
                    loadNextMatchFragment(savedInstanceState)
                }
                favoritesEvent -> {
                    loadFavoritesEventFragment(savedInstanceState)
                }
            }
            true
        })
        bottom_navigation.selectedItemId = prevmatch
    }

    private fun loadPrevMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, PrevEventFragment(), PrevEventFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, NextEventFragment(), NextEventFragment::class.simpleName)
                    .commit()
        }
    }


    private fun loadFavoritesEventFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteEventFragment(), FavoriteEventFragment::class.simpleName)
                    .commit()
        }
    }
}

