package com.appgue.majorleaguesoccer.details

import com.appgue.majorleaguesoccer.model.Team

/**
 * Created by Andri on 5/19/2018.
 */
interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}