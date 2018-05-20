package com.appgue.majorleaguesoccer.eventdetails

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.R.id.versus_text
import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.db.Favorite
import com.appgue.majorleaguesoccer.db.database
import com.appgue.majorleaguesoccer.details.TeamDetailPresenter
import com.appgue.majorleaguesoccer.details.TeamDetailView
import com.appgue.majorleaguesoccer.model.Event
import com.appgue.majorleaguesoccer.model.Team
import com.appgue.majorleaguesoccer.util.invisible
import com.appgue.majorleaguesoccer.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventDetailActivity : AppCompatActivity(), EventDetailView {
    private lateinit var presenter: EventDetailPresenter
    private lateinit var events: Event
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var homeTeam: TextView
    private lateinit var awayTeam: TextView
    private lateinit var homeScore: TextView
    private lateinit var awayScore: TextView
    private lateinit var dateEvent: TextView
    private lateinit var versusText: TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        id = intent.getStringExtra("id")
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            dateEvent = textView { this.gravity = Gravity.CENTER }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                versusText = textView {
                                    id = versus_text
                                    text = "VS"
                                }.lparams{centerHorizontally()}

                                homeScore = textView {
                                    textSize = 22f
                                    typeface = Typeface.DEFAULT_BOLD
                                }.lparams {leftOf(versus_text)}

                                awayScore = textView {
                                    textSize = 22f
                                    typeface = Typeface.DEFAULT_BOLD
                                }.lparams {rightOf(versus_text)}
                            }


                            homeTeam = textView {
                                this.gravity = Gravity.CENTER
                            }

                            awayTeam = textView {
                                this.gravity = Gravity.CENTER
                            }

                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        //favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(id)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(id)
        }
    }

//    private fun favoriteState(){
//        database.use {
//            val result = select(Favorite.TABLE_FAVORITE)
//                    .whereArgs("(TEAM_ID = {id})",
//                            "id" to id)
//            val favorite = result.parseList(classParser<Favorite>())
//            if (!favorite.isEmpty()) isFavorite = true
//        }
//    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<Event>) {
//        events = Event(data[0].idEvent,
//                data[0].strHomeTeam,
//                data[0].strAwayTeam,
//                data[0].dateEvent,
//                data[0].intHomeScore,
//                data[0].intAwayScore)
        swipeRefresh.isRefreshing = false
        //Picasso.get().load(data[0].teamBadge).into(teamBadge)
        homeTeam.text = data[0].strHomeTeam
        awayTeam.text = data[0].strAwayTeam
        homeScore.text = data[0].intHomeScore
        awayScore.text = data[0].intAwayScore
        dateEvent.text = data[0].dateEvent
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        //setFavorite()
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                true
//            }
//            R.id.add_to_favorite -> {
//                if (isFavorite) removeFromFavorite() else addToFavorite()
//
//                isFavorite = !isFavorite
//                setFavorite()
//
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    private fun addToFavorite(){
//        try {
//            database.use {
//                insert(Favorite.TABLE_FAVORITE,
//                        Favorite.TEAM_ID to teams.teamId,
//                        Favorite.TEAM_NAME to teams.teamName,
//                        Favorite.TEAM_BADGE to teams.teamBadge)
//            }
//            snackbar(swipeRefresh, "Added to favorite").show()
//        } catch (e: SQLiteConstraintException){
//            snackbar(swipeRefresh, e.localizedMessage).show()
//        }
//    }

//    private fun removeFromFavorite(){
//        try {
//            database.use {
//                delete(Favorite.TABLE_FAVORITE, "(TEAM_ID = {id})",
//                        "id" to id)
//            }
//            snackbar(swipeRefresh, "Removed to favorite").show()
//        } catch (e: SQLiteConstraintException){
//            snackbar(swipeRefresh, e.localizedMessage).show()
//        }
//    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}
