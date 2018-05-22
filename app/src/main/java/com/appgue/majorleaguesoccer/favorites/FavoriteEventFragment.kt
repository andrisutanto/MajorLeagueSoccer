package com.appgue.majorleaguesoccer.favorites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.db.FavoriteEvent
import com.appgue.majorleaguesoccer.db.databaseEvent
import com.appgue.majorleaguesoccer.eventdetails.EventDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by ITP on 22-May-18.
 */
class FavoriteEventFragment : Fragment(), AnkoComponent<Context> {
    private var favoritesEvent: MutableList<FavoriteEvent> = mutableListOf()
    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteEventAdapter(favoritesEvent){
            ctx.startActivity<EventDetailActivity>("id" to "${it.eventId}",
                    "idHomeTeam" to "${it.teamHomeId}",
                    "idAwayTeam" to "${it.teamAwayId}")
        }

        listEvent.adapter = adapter
        showFavoriteEvent()
        swipeRefresh.onRefresh {
            favoritesEvent.clear()
            showFavoriteEvent()
        }
    }

    private fun showFavoriteEvent(){
        context?.databaseEvent?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteEvent.TABLE_FAVORITE_EVENT)
            val favoriteEvents = result.parseList(classParser<FavoriteEvent>())
            favoritesEvent.addAll(favoriteEvents)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                listEvent = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}