package com.appgue.majorleaguesoccer.previous

import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.api.TheSportDBApi
import com.appgue.majorleaguesoccer.model.EventResponse
import com.appgue.majorleaguesoccer.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PrevEventPresenter(private val view: PrevEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPrevEventList(leagueId: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                    gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPrevMatch(leagueId)),
                    EventResponse::class.java
                    )
            }
            view.showPrevEventList(data.await().events)
            view.hideLoading()
        }
    }
}