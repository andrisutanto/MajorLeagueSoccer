package com.appgue.majorleaguesoccer.previous

import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.api.TheSportDBApi
import com.appgue.majorleaguesoccer.model.EventResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PrevEventPresenter(private val view: PrevEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson) {

    fun getPrevEventList(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPrevMatch(leagueId)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showPrevEventList(data.events)
            }
        }
    }
}