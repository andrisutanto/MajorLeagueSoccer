package com.appgue.majorleaguesoccer.teams

import com.appgue.majorleaguesoccer.model.Team

/**
 * Created by Andri on 5/19/2018.
 */
interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}