package com.appgue.majorleaguesoccer.teams

import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.api.TheSportDBApi
import com.appgue.majorleaguesoccer.model.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Andri on 5/19/2018.
 */
class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson) {

    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}