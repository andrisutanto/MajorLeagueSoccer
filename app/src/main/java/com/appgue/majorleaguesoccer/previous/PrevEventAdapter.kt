package com.appgue.majorleaguesoccer.previous

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.R.id.team_home
import com.appgue.majorleaguesoccer.R.id.score_home
import com.appgue.majorleaguesoccer.R.id.score_away
import com.appgue.majorleaguesoccer.R.id.team_away
import com.appgue.majorleaguesoccer.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PrevEventAdapter(private val prevevent: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<PrevEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevEventViewHolder {
        return PrevEventViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PrevEventViewHolder, position: Int) {
        holder.bindItem(prevevent[position], listener)
    }

    override fun getItemCount(): Int = prevevent.size

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)

                textView {
                    id = R.id.event_date
                    textSize = 12f
                    textColor = R.color.colorPrimary
                }.lparams{
                    margin = dip(5)
                    centerHorizontally()
                }

                textView{
                    id = R.id.versus
                    textSize = 18f
                    text = "VS"
                }.lparams{
                    centerHorizontally()
                    centerVertically()
                    below(R.id.event_date)
                }

                textView {
                    id = R.id.team_home
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams{
                    margin = dip(5)
                    leftOf(R.id.score_home)
                }

                textView {
                    id = R.id.score_home
                    textSize = 22f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams{
                    margin = dip(15)
                    leftOf(R.id.versus)
                    centerVertically()
                }

                textView {
                    id = R.id.score_away
                    textSize = 22f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams{
                    margin = dip(15)
                    rightOf(R.id.versus)
                    centerVertically()
                }

                textView {
                    id = R.id.team_away
                    textSize = 16f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams{
                    margin = dip(5)
                    rightOf(R.id.score_away)
                }
            }
        }
    }

}

class PrevEventViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val eventDate: TextView = view.find(R.id.event_date)
    private val teamHome: TextView = view.find(R.id.team_home)
    private val scoreHome: TextView = view.find(R.id.score_home)
    private val teamAway: TextView = view.find(R.id.team_away)
    private val scoreAway: TextView = view.find(R.id.score_away)

    fun bindItem(prevevent: Event, listener: (Event) -> Unit) {
        eventDate.text = prevevent.dateEvent
        teamHome.text = prevevent.strHomeTeam
        scoreHome.text = prevevent.intHomeScore
        teamAway.text = prevevent.strAwayTeam
        scoreAway.text = prevevent.intAwayScore
        itemView.onClick { listener(prevevent) }
    }
}