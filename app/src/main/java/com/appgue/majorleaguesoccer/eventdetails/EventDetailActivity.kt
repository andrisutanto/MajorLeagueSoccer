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
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.R.id.*
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
    private lateinit var homeShot: TextView
    private lateinit var awayShot: TextView
    private lateinit var homeFormation: TextView
    private lateinit var awayFormation: TextView
    private lateinit var homeGoal: TextView
    private lateinit var awayGoal: TextView
    private lateinit var homeGoalkeeper: TextView
    private lateinit var awayGoalkeeper: TextView
    private lateinit var homeDefense: TextView
    private lateinit var awayDefense: TextView
    private lateinit var homeMidfield: TextView
    private lateinit var awayMidfield: TextView
    private lateinit var homeForward: TextView
    private lateinit var awayForward: TextView
    private lateinit var dateEvent: TextView
    private lateinit var versusText: TextView
    private lateinit var teamBadgeHome: ImageView
    private lateinit var teamBadgeAway: ImageView

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

                            dateEvent = textView {
                                this.gravity = Gravity.CENTER
                                textColorResource = R.color.colorPrimary
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = dip(50))

                                versusText = textView {
                                    id = versus_text
                                    text = "VS"
                                    textSize = 20f
                                    this.gravity = Gravity.CENTER_VERTICAL
                                }.lparams{
                                    centerHorizontally()
                                    centerVertically()
                                }

                                homeScore = textView {
                                    id = score_home_detail
                                    textSize = 30f
                                    typeface = Typeface.DEFAULT_BOLD
                                    this.gravity = Gravity.CENTER
                                }.lparams {
                                    leftOf(versus_text)
                                    rightMargin = dip(5)
                                    centerHorizontally()
                                    centerVertically()
                                }

                                awayScore = textView {
                                    id = score_away_detail
                                    textSize = 30f
                                    typeface = Typeface.DEFAULT_BOLD
                                    this.gravity = Gravity.CENTER
                                }.lparams {
                                    rightOf(versus_text)
                                    leftMargin = dip(5)
                                    centerHorizontally()
                                    centerVertically()
                                }

                                teamBadgeHome = imageView {
                                    id = team_logo_home
                                }.lparams{
                                    height = dip(50)
                                    width = dip(50)
                                    alignParentStart()
                                    leftOf(score_home_detail)
                                    bottomMargin = dip(10)
                                }

                                teamBadgeAway = imageView {
                                    id = team_logo_away
                                }.lparams{
                                    height = dip(50)
                                    width = dip(50)
                                    alignParentEnd()
                                    rightOf(score_away_detail)
                                    bottomMargin = dip(10)
                                }
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                homeTeam = textView {
                                    id = team_home_detail
                                    textSize = 18f
                                    typeface = Typeface.DEFAULT_BOLD
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    alignParentStart()
                                    width = dip(150)
                                    centerHorizontally()
                                }

                                awayTeam = textView {
                                    id = team_away_detail
                                    textSize = 18f
                                    typeface = Typeface.DEFAULT_BOLD
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams {
                                    alignParentEnd()
                                    width = dip(150)
                                    centerHorizontally()
                                }

                                homeFormation = textView {
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    alignParentLeft()
                                    below(team_home_detail)
                                    width = dip(150)
                                    centerHorizontally()
                                }

                                awayFormation = textView {
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams {
                                    alignParentRight()
                                    below(team_away_detail)
                                    width = dip(150)
                                    centerHorizontally()
                                }
                            }

                            view {
                                backgroundColorResource = R.color.colorPrimary
                            }.lparams(width = matchParent, height = dip(1)) {
                                topMargin = dip(10)
                                bottomMargin = dip(10)
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                textView {
                                    id = goal_text
                                    text = "Goals"
                                    textColorResource = R.color.colorPrimary
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    centerHorizontally()
                                }

                                homeGoal = textView {
                                    this.gravity = Gravity.LEFT
                                }.lparams{
                                    alignParentStart()
                                    width = dip(100)
                                    height = wrapContent
                                }

                                awayGoal = textView {
                                    this.gravity = Gravity.RIGHT
                                }.lparams{
                                    alignParentEnd()
                                    width = dip(100)
                                    height = wrapContent
                                }
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                textView {
                                    id = shot_text
                                    text = "Shots"
                                    textColorResource = R.color.colorPrimary
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    centerHorizontally()
                                }

                                homeShot = textView {
                                    this.gravity = Gravity.LEFT
                                }.lparams{
                                    alignParentStart()
                                    leftOf(shot_text)
                                    width = dip(100)
                                    height = wrapContent
                                }

                                awayShot = textView {
                                    this.gravity = Gravity.RIGHT
                                }.lparams{
                                    alignParentEnd()
                                    rightOf(shot_text)
                                    width = dip(100)
                                    height = wrapContent
                                }
                            }

                            view {
                                backgroundColorResource = R.color.colorPrimary
                            }.lparams(width = matchParent, height = dip(1)) {
                                topMargin = dip(10)
                                bottomMargin = dip(10)
                            }

                            textView {
                                id = lineup_text
                                text = "Lineups"
                                this.gravity = Gravity.CENTER_HORIZONTAL
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                textView {
                                    id = goalkeeper_text
                                    text = "Goalkeeper"
                                    textColorResource = R.color.colorPrimary
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    centerHorizontally()
                                    bottomMargin = dip(10)
                                }

                                homeGoalkeeper = textView {
                                    this.gravity = Gravity.LEFT
                                }.lparams{
                                    alignParentStart()
                                    leftOf(goalkeeper_text)
                                    width = dip(100)
                                    height = wrapContent
                                }

                                awayGoalkeeper = textView {
                                    this.gravity = Gravity.RIGHT
                                }.lparams{
                                    alignParentEnd()
                                    rightOf(goalkeeper_text)
                                    width = dip(100)
                                    height = wrapContent
                                }
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                textView {
                                    id = defense_text
                                    text = "Defense"
                                    textColorResource = R.color.colorPrimary
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    centerHorizontally()
                                    bottomMargin = dip(10)
                                }

                                homeDefense = textView {
                                    this.gravity = Gravity.LEFT
                                }.lparams{
                                    alignParentStart()
                                    leftOf(defense_text)
                                    width = dip(100)
                                    height = wrapContent
                                }

                                awayDefense = textView {
                                    this.gravity = Gravity.RIGHT
                                }.lparams{
                                    alignParentEnd()
                                    rightOf(defense_text)
                                    width = dip(100)
                                    height = wrapContent
                                }
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                textView {
                                    id = midfield_text
                                    text = "Midfield"
                                    textColorResource = R.color.colorPrimary
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    centerHorizontally()
                                    bottomMargin = dip(10)
                                }

                                homeMidfield = textView {
                                    this.gravity = Gravity.LEFT
                                }.lparams{
                                    alignParentStart()
                                    leftOf(midfield_text)
                                    width = dip(100)
                                    height = wrapContent
                                }

                                awayMidfield = textView {
                                    this.gravity = Gravity.RIGHT
                                }.lparams{
                                    alignParentEnd()
                                    rightOf(midfield_text)
                                    width = dip(100)
                                    height = wrapContent
                                }
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                textView {
                                    id = forward_text
                                    text = "Forward"
                                    textColorResource = R.color.colorPrimary
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams{
                                    centerHorizontally()
                                }

                                homeForward = textView {
                                    this.gravity = Gravity.LEFT
                                }.lparams{
                                    alignParentStart()
                                    leftOf(forward_text)
                                    width = dip(100)
                                    height = wrapContent
                                }

                                awayForward = textView {
                                    this.gravity = Gravity.RIGHT
                                }.lparams{
                                    alignParentEnd()
                                    rightOf(forward_text)
                                    width = dip(100)
                                    height = wrapContent
                                }
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
        Picasso.get().load("https://www.thesportsdb.com/images/media/team/badge/qtwprq1473536474.png").into(teamBadgeHome)
        Picasso.get().load("https://www.thesportsdb.com/images/media/team/badge/qtwprq1473536474.png").into(teamBadgeAway)
        homeTeam.text = data[0].strHomeTeam
        awayTeam.text = data[0].strAwayTeam
        homeScore.text = data[0].intHomeScore
        awayScore.text = data[0].intAwayScore
        homeShot.text = data[0].intHomeShots
        awayShot.text = data[0].intAwayShots
        homeGoal.text = data[0].strHomeGoalDetails
        awayGoal.text = data[0].strAwayGoalDetails
        homeFormation.text = data[0].strHomeFormation
        awayFormation.text = data[0].strAwayFormation

        homeGoalkeeper.text = data[0].strHomeLineupGoalkeeper
        awayGoalkeeper.text = data[0].strAwayLineupGoalkeeper
        homeDefense.text = data[0].strHomeLineupDefense
        awayDefense.text = data[0].strAwayLineupDefense
        homeMidfield.text = data[0].strHomeLineupMidfield
        awayMidfield.text = data[0].strAwayLineupMidfield
        homeForward.text = data[0].strHomeLineupForward
        awayForward.text = data[0].strAwayLineupForward

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
