package com.appgue.majorleaguesoccer.favorites

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.db.FavoriteEvent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by ITP on 22-May-18.
 */

class FavoriteEventAdapter(private val favoriteEvent: List<FavoriteEvent>, private val listener: (FavoriteEvent) -> Unit)
    : RecyclerView.Adapter<FavoriteEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        return FavoriteEventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bindItem(favoriteEvent[position], listener)
    }

    override fun getItemCount(): Int = favoriteEvent.size

}

class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(10)

                textView {
                    id = R.id.event_date_favorite
                    textSize = 12f
                    textColor = R.color.colorPrimary
                }.lparams{
                    margin = dip(5)
                    centerHorizontally()
                }

                textView{
                    id = R.id.versus_favorite
                    textSize = 18f
                    text = "VS"
                }.lparams{
                    centerHorizontally()
                    centerVertically()
                    below(R.id.event_date_favorite)
                }

                textView {
                    id = R.id.team_home_favorite
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams{
                    margin = dip(5)
                    leftOf(R.id.score_home_favorite)
                }

                textView {
                    id = R.id.score_home_favorite
                    textSize = 22f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams{
                    margin = dip(15)
                    leftOf(R.id.versus_favorite)
                    centerVertically()
                }

                textView {
                    id = R.id.score_away_favorite
                    textSize = 22f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams{
                    margin = dip(15)
                    rightOf(R.id.versus_favorite)
                    centerVertically()
                }

                textView {
                    id = R.id.team_away_favorite
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams{
                    margin = dip(5)
                    rightOf(R.id.score_away_favorite)
                }
            }
        }
    }

}

class FavoriteEventViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val teamHomeFavorite: TextView = view.find(R.id.team_home_favorite)
    private val teamAwayFavorite: TextView = view.find(R.id.team_away_favorite)
    private val eventDateFavorite: TextView = view.find(R.id.event_date_favorite)
    private val scoreHomeFavorite: TextView = view.find(R.id.score_home_favorite)
    private val scoreAwayFavorite: TextView = view.find(R.id.score_away_favorite)

    fun bindItem(favoriteEvent: FavoriteEvent, listener: (FavoriteEvent) -> Unit) {
        teamHomeFavorite.text = favoriteEvent.teamHome
        teamAwayFavorite.text = favoriteEvent.teamAway
        eventDateFavorite.text = favoriteEvent.eventDate
        scoreHomeFavorite.text = favoriteEvent.scoreHome
        scoreAwayFavorite.text = favoriteEvent.scoreAway
        itemView.onClick { listener(favoriteEvent) }
    }
}