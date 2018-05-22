package com.appgue.majorleaguesoccer.next

import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.api.TheSportDBApi
import com.appgue.majorleaguesoccer.model.EventResponse
import com.appgue.majorleaguesoccer.next.NextEventView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextEventPresenter(private val view: NextEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson) {

    fun getNextEventList(leagueId: String?) {
        view.showLoading()
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch(leagueId)),
                        EventResponse::class.java
                )
            }
            view.showNextEventList(data.await().events)
            view.hideLoading()
        }
    }
}