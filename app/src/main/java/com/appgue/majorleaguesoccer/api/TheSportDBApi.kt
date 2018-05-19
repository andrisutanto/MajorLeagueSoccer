package com.appgue.majorleaguesoccer.api

import com.appgue.majorleaguesoccer.BuildConfig

/**
 * Created by Andri on 5/19/2018.
 */
object TheSportDBApi {
    fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    fun getTeamDetail(teamId: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }
}