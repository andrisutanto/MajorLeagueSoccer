package com.appgue.majorleaguesoccer.previous

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
import android.widget.ProgressBar
import com.appgue.majorleaguesoccer.R
import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.details.TeamDetailActivity
import com.appgue.majorleaguesoccer.eventdetails.EventDetailActivity
import com.appgue.majorleaguesoccer.model.Event
import com.appgue.majorleaguesoccer.util.invisible
import com.appgue.majorleaguesoccer.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PrevEventFragment : Fragment(), AnkoComponent<Context>, PrevEventView {

    private var prevevent: MutableList<Event> = mutableListOf()
    private lateinit var presenter: PrevEventPresenter
    private lateinit var adapter: PrevEventAdapter
    //private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueId: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //val spinnerItems = resources.getStringArray(league)
        //val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        //spinner.adapter = spinnerAdapter

        adapter = PrevEventAdapter(prevevent) {
            ctx.startActivity<EventDetailActivity>(
                    "id" to "${it.idEvent}",
                    "idHomeTeam" to "${it.idHomeTeam}",
                    "idAwayTeam" to "${it.idAwayTeam}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PrevEventPresenter(this, request, gson)
        //leagueId = "4346"
        leagueId = "4328"
        presenter.getPrevEventList(leagueId)
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                leagueName = "American%20Major%20League%20Soccer"
//                presenter.getTeamList(leagueName)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }

        swipeRefresh.onRefresh {
            presenter.getPrevEventList(leagueId)
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

//            spinner = spinner {
//                id = R.id.spinner
//            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.listEvent
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPrevEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        prevevent.clear()
        prevevent.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
