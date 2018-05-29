package com.appgue.majorleaguesoccer.next

import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.api.TheSportDBApi
import com.appgue.majorleaguesoccer.model.EventResponse
import com.appgue.majorleaguesoccer.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextEventPresenter(private val view: NextEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextEventList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
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