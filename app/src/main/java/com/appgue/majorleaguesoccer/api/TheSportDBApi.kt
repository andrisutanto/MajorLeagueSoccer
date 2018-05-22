package com.appgue.majorleaguesoccer.api

import com.appgue.majorleaguesoccer.BuildConfig

/**
 * Created by Andri on 5/19/2018.
 */
object TheSportDBApi {
    fun getPrevMatch(leagueId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + leagueId
    }

    fun getNextMatch(leagueId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + leagueId
    }

    fun getEventDetail(eventId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + eventId
    }
}